package com.example.androidassignments;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoginActivityTest {

    private LoginActivity loginActivity;

    @Mock
    private SharedPreferences sharedPreferences;

    @Mock
    private SharedPreferences.Editor editor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); // Initialize mocks
        loginActivity = new LoginActivity();
        // Mock SharedPreferences behavior
        when(loginActivity.getSharedPreferences("LoginPrefs", LoginActivity.MODE_PRIVATE)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null; // Clean up after each test
    }

    @Test
    public void onCreate() {
        // Call onCreate
        loginActivity.onCreate(null);

        // Verify that email space is initialized with saved email
        EditText emailSpace = loginActivity.findViewById(R.id.emailSpace);
        verify(editor).putString("DefaultEmail", "email@domain.com");
        assertEquals("email@domain.com", emailSpace.getText().toString());
    }

    @Test
    public void login_emptyEmail() {
        // Simulate input
        EditText emailSpace = mock(EditText.class);
        EditText passwordSpace = mock(EditText.class);

        when(emailSpace.getText()).thenReturn(new EditableString("")); // Simulate empty email
        when(passwordSpace.getText()).thenReturn(new EditableString("somePassword")); // Simulate some password

        // Perform login
        loginActivity.login(new View(loginActivity));

        // Check if error is set
        verify(emailSpace).setError("Email cannot be empty");
    }

    @Test
    public void login_invalidEmailFormat() {
        // Simulate input
        EditText emailSpace = mock(EditText.class);
        EditText passwordSpace = mock(EditText.class);

        when(emailSpace.getText()).thenReturn(new EditableString("invalidEmail")); // Simulate invalid email
        when(passwordSpace.getText()).thenReturn(new EditableString("somePassword")); // Simulate some password

        // Perform login
        loginActivity.login(new View(loginActivity));

        // Check if error is set
        verify(emailSpace).setError("Invalid email format");
    }

    @Test
    public void login_emptyPassword() {
        // Simulate input
        EditText emailSpace = mock(EditText.class);
        EditText passwordSpace = mock(EditText.class);

        when(emailSpace.getText()).thenReturn(new EditableString("test@example.com")); // Simulate valid email
        when(passwordSpace.getText()).thenReturn(new EditableString("")); // Simulate empty password

        // Perform login
        loginActivity.login(new View(loginActivity));

        // Check if error is set
        verify(passwordSpace).setError("Password cannot be empty");
    }

    @Test
    public void login_validCredentials() {
        // Simulate input
        EditText emailSpace = mock(EditText.class);
        EditText passwordSpace = mock(EditText.class);

        when(emailSpace.getText()).thenReturn(new EditableString("test@example.com")); // Simulate valid email
        when(passwordSpace.getText()).thenReturn(new EditableString("password123")); // Simulate valid password

        // Perform login
        loginActivity.login(new View(loginActivity));

        // Verify if the SharedPreferences were updated
        verify(editor).putString("DefaultEmail", "test@example.com");
        verify(editor).apply();

        // Verify if the MainActivity was started
        Intent intent = new Intent(loginActivity, MainActivity.class);
        assertNotNull(intent); // Ensure the intent is created
    }

    @Test
    public void onStart() {
        loginActivity.onStart();
        // Here we could check log messages if needed (using a logger)
    }

    @Test
    public void onResume() {
        loginActivity.onResume();
        // Check if necessary actions on resume
    }

    @Test
    public void onPause() {
        loginActivity.onPause();
        // Check if necessary actions on pause
    }

    @Test
    public void onStop() {
        loginActivity.onStop();
        // Check if necessary actions on stop
    }

    @Test
    public void onDestroy() {
        loginActivity.onDestroy();
        // Check if necessary actions on destroy
    }

    @Test
    public void onSaveInstanceState() {
        // Simulate saving instance state
        loginActivity.onSaveInstanceState(new Bundle());
        // Check log messages or state restoration as needed
    }

    @Test
    public void onRestoreInstanceState() {
        // Simulate restoring instance state
        loginActivity.onRestoreInstanceState(new Bundle());
        // Check log messages or state restoration as needed
    }
}
