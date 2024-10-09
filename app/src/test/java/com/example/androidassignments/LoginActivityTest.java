package com.example.androidassignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)

public class LoginActivityTest {

    private LoginActivity loginActivity;
    private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().get();
        sharedPreferences = ApplicationProvider.getApplicationContext()
                .getSharedPreferences("LoginPrefs", AppCompatActivity.MODE_PRIVATE);
    }

    @After
    public void tearDown() {
        sharedPreferences.edit().clear().apply();
    }

    @Test
    public void testSavedEmailIsLoaded() {
        // Arrange
        sharedPreferences.edit().putString("DefaultEmail", "email@domain.com").apply();

        // Act
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().get();

        // Assert
        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        assertEquals("email@domain.com", emailSpace.getText().toString());
    }

    @Test
    public void testLoginWithInvalidEmailFormat() {
        // Arrange
        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        emailSpace.setText("invalid-email");

        // Act
        loginActivity.login(null); // Simulating button click

        // Assert
        assertEquals("Invalid email format", emailSpace.getError());
    }

    @Test
    public void testLoginWithEmptyPassword() {
        // Arrange
        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        emailSpace.setText("test@example.com"); // valid email
        EditText passwordSpace = loginActivity.findViewById(R.id.passwordSpace);

        // Act
        loginActivity.login(null);

        // Assert
        assertEquals("Password cannot be empty", passwordSpace.getError());
    }

    @Test
    public void testSuccessfulLogin() {
        // Arrange
        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        emailSpace.setText("test@example.com");
        EditText passwordSpace = loginActivity.findViewById(R.id.passwordSpace);
        passwordSpace.setText("password123"); // Assuming this is a valid password

        // Act
        loginActivity.login(null);


        Intent intent = shadowOf(loginActivity).getNextStartedActivity();
        assertNotNull(intent);
        assertEquals(MainActivity.class.getName(), intent.getComponent().getClassName());

        String savedEmail = sharedPreferences.getString("DefaultEmail", null);
        assertEquals("test@example.com", savedEmail);
    }

    @Test
    public void testOnStart() {
        // Simulate onStart lifecycle method.
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().get();

        // Verify that the log message for onStart is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onCreate called")));
    }

    @Test
    public void testOnResume() {
        // Simulate onResume lifecycle method.
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().get();

        // Verify that the log message for onResume is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onResume called")));
    }

    @Test
    public void testOnPause() {
        // Simulate onPause lifecycle method.
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().pause().get();

        // Verify that the log message for onPause is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onPause called")));
    }

    @Test
    public void testOnStop() {
        // Simulate onStop lifecycle method.
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().stop().get();

        // Verify that the log message for onStop is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onStop called")));
    }

    @Test
    public void testOnDestroy() {
        // Simulate onDestroy lifecycle method.
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().destroy().get();

        // Verify that the log message for onDestroy is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onDestroy called")));
    }

    @Test
    public void testOnSaveInstanceState() {
        // Simulate saving the instance state.
        Bundle outState = new Bundle();
        loginActivity.onSaveInstanceState(outState);

        // Verify that the log message for onSaveInstanceState is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onSaveInstanceState called")));
    }

    @Test
    public void testOnRestoreInstanceState() {
        // Simulate restoring the instance state.
        Bundle savedInstanceState = new Bundle();
        loginActivity.onRestoreInstanceState(savedInstanceState);

        // Verify that the log message for onRestoreInstanceState is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onRestoreInstanceState called")));
    }



}