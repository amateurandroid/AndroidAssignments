package com.example.androidassignments;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertTrue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

@RunWith(AndroidJUnit4.class)
public class ChatWindowInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ChatWindow> activityScenarioRule = new ActivityScenarioRule<>(ChatWindow.class);

    @Test
    public void testSendMessage() {
        Espresso.onView(ViewMatchers.withId(R.id.messageEditText))
                .perform(typeText("Hello, World!"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.sendButton)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadExistingMessages() {
        activityScenarioRule.getScenario().recreate();

        Espresso.onView(ViewMatchers.withId(R.id.chatListView))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText("Hello, World!"))));
    }
}
