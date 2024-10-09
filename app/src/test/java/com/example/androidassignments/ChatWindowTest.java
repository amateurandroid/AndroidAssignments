package com.example.androidassignments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class ChatWindowTest {

    private ChatWindow chatWindow;
    private ActivityController<ChatWindow> controller;
    private EditText messageEditText;
    private Button sendButton;
    private ListView chatListView;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(ChatWindow.class).create().start().resume();
        chatWindow = controller.get();

        messageEditText = chatWindow.findViewById(R.id.messageEditText);
        sendButton = chatWindow.findViewById(R.id.sendButton);
        chatListView = chatWindow.findViewById(R.id.chatListView);
    }

    @After
    public void tearDown() {
        controller.pause().stop().destroy();
    }

    @Test
    public void testSendButtonAddsMessage() {
        // Verify that the ListView and other components are initialized
        assertNotNull(chatListView);
        assertNotNull(messageEditText);
        assertNotNull(sendButton);

        // Set a message in the EditText
        messageEditText.setText("Hello World");

        // Perform a click on the Send button
        sendButton.performClick();

        // Check if the message has been added to the ListView
        assertEquals(1, chatWindow.chatMessages.size());
        assertEquals("Hello World", chatWindow.chatMessages.get(0));

        // Verify that the EditText is cleared after sending the message
        assertEquals("", messageEditText.getText().toString());
    }

    @Test
    public void testEditTextClearedAfterSend() {
        // Set a test message in the EditText
        String testMessage = "Test message";
        messageEditText.setText(testMessage);

        // Simulate button click
        sendButton.performClick();

        // Verify that the EditText is cleared after sending the message
        assertEquals("", messageEditText.getText().toString());
    }



    @Test
    public void testMultipleMessages() {
        // Add multiple messages and test if the ListView handles them
        messageEditText.setText("First message");
        sendButton.performClick();

        messageEditText.setText("Second message");
        sendButton.performClick();

        // Verify that both messages are added
        assertEquals(2, chatWindow.chatMessages.size());
        assertEquals("First message", chatWindow.chatMessages.get(0));
        assertEquals("Second message", chatWindow.chatMessages.get(1));

        // Check the ListView count
        assertEquals(2, chatListView.getAdapter().getCount());
    }

    @Test
    public void testSendButtonIgnoresEmptyMessages() {
        // Set an empty message in the EditText
        messageEditText.setText("");

        // Simulate button click
        sendButton.performClick();

        // Verify that no message was added to the chatMessages list or ListView
        assertEquals(0, chatWindow.chatMessages.size());
        assertEquals(0, chatListView.getAdapter().getCount());
    }
}
