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

        sharedPreferences.edit().putString("DefaultEmail", "email@domain.com").apply();

        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().get();

        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        assertEquals("email@domain.com", emailSpace.getText().toString());
    }

    @Test
    public void testLoginWithInvalidEmailFormat() {

        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        emailSpace.setText("xyzabc");

        loginActivity.login(null);

        assertEquals("Invalid email format", emailSpace.getError());
    }

    @Test
    public void testLoginWithEmptyPassword() {

        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        emailSpace.setText("test@sample.com"); // valid email
        EditText passwordSpace = loginActivity.findViewById(R.id.passwordSpace);

        loginActivity.login(null);

        assertEquals("Password cannot be empty", passwordSpace.getError());
    }

    @Test
    public void testSuccessfulLogin() {

        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        emailSpace.setText("test@example.com");
        EditText passwordSpace = loginActivity.findViewById(R.id.passwordSpace);
        passwordSpace.setText("password123");

        loginActivity.login(null);

        Intent intent = shadowOf(loginActivity).getNextStartedActivity();
        assertNotNull(intent);
        assertEquals(MainActivity.class.getName(), intent.getComponent().getClassName());

        String savedEmail = sharedPreferences.getString("DefaultEmail", null);
        assertEquals("test@example.com", savedEmail);
    }

    @Test
    public void testOnStart() {

        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().get();

        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onCreate called")));
    }

    @Test
    public void testOnResume() {

        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().get();

        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onResume called")));
    }

    @Test
    public void testOnPause() {

        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().pause().get();

        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onPause called")));
    }

    @Test
    public void testOnStop() {

        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().stop().get();

        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onStop called")));
    }

    @Test
    public void testOnDestroy() {

        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().destroy().get();

        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onDestroy called")));
    }

    @Test
    public void testOnSaveInstanceState() {

        Bundle outState = new Bundle();
        loginActivity.onSaveInstanceState(outState);

        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onSaveInstanceState called")));
    }

    @Test
    public void testOnRestoreInstanceState() {

        Bundle savedInstanceState = new Bundle();
        loginActivity.onRestoreInstanceState(savedInstanceState);

        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("LoginActivity") && log.msg.equals("onRestoreInstanceState called")));
    }



}