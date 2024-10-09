package com.example.androidassignments;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.test.core.app.ApplicationProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowLog;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;
    private ActivityController<MainActivity> controller;

    @Before
    public void setUp() throws Exception {
        controller = Robolectric.buildActivity(MainActivity.class).create();
        mainActivity = controller.get();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
        controller = null;
    }

    @Test
    public void testOnCreate() {
        controller.start();
        assertNotNull(mainActivity);
        assertNotNull(mainActivity.findViewById(R.id.forwardButton));
        assertNotNull(mainActivity.findViewById(R.id.button_start_chat));
    }



    @Test
    public void testOnSaveInstanceState() {
        Bundle outState = new Bundle();
        mainActivity.onSaveInstanceState(outState);
        assertNotNull(outState);
    }

    @Test
    public void testOnRestoreInstanceState() {
        Bundle savedInstanceState = new Bundle();
        savedInstanceState.putString("key", "value");
        mainActivity.onRestoreInstanceState(savedInstanceState);
        assertNotNull(savedInstanceState);
    }

    @Test
    public void testOnActivityResult() {
        Intent data = new Intent();
        data.putExtra("Response", "Test Response");

        mainActivity.onActivityResult(10, Activity.RESULT_OK, data);
        assertEquals("Test Response", data.getStringExtra("Response"));
    }


    @Test
    public void testForwardButtonClick() {
        Button forwardButton = mainActivity.findViewById(R.id.forwardButton);
        assertNotNull(forwardButton);

        forwardButton.performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(mainActivity);
        Intent nextIntent = shadowActivity.getNextStartedActivity();

        assertNotNull(nextIntent);
        assertEquals(ListItemsActivity.class.getName(), nextIntent.getComponent().getClassName());
    }

    @Test
    public void testStartChatButtonClick() {
        Button startChatButton = mainActivity.findViewById(R.id.button_start_chat);
        assertNotNull(startChatButton);

        startChatButton.performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(mainActivity);
        Intent nextIntent = shadowActivity.getNextStartedActivity();

        assertNotNull(nextIntent);
        assertEquals(ChatWindow.class.getName(), nextIntent.getComponent().getClassName());
    }



    @Test
    public void testOnStart() {
        // Simulate onStart lifecycle method.
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().get();

        // Verify that the log message for onStart is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("MainActivity") && log.msg.equals("onCreate called")));
    }

    @Test
    public void testOnResume() {
        // Simulate onResume lifecycle method.
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();

        // Verify that the log message for onResume is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("MainActivity") && log.msg.equals("onResume called")));
    }

    @Test
    public void testOnPause() {
        // Simulate onPause lifecycle method.
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().pause().get();

        // Verify that the log message for onPause is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("MainActivity") && log.msg.equals("onPause called")));
    }

    @Test
    public void testOnStop() {
        // Simulate onStop lifecycle method.
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().stop().get();

        // Verify that the log message for onStop is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("MainActivity") && log.msg.equals("onStop called")));
    }

    @Test
    public void testOnDestroy() {
        // Simulate onDestroy lifecycle method.
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().destroy().get();

        // Verify that the log message for onDestroy is correct.
        assertTrue(ShadowLog.getLogs().stream().anyMatch(log ->
                log.tag.equals("MainActivity") && log.msg.equals("onDestroy called")));
    }


}
