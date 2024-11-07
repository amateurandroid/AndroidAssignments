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

        assertNotNull(chatListView);
        assertNotNull(messageEditText);
        assertNotNull(sendButton);

        messageEditText.setText("Hello World");

        sendButton.performClick();


        assertEquals("", messageEditText.getText().toString());
    }

    @Test
    public void testEditTextClearedAfterSend() {

        String testMessage = "Test message";
        messageEditText.setText(testMessage);

        sendButton.performClick();

        assertEquals("", messageEditText.getText().toString());
    }



    @Test
    public void testMultipleMessages() {

        messageEditText.setText("First message");
        sendButton.performClick();

        messageEditText.setText("Second message");
        sendButton.performClick();

        assertEquals(2, chatListView.getAdapter().getCount());
    }

    @Test
    public void testSendButtonIgnoresEmptyMessages() {

        messageEditText.setText("");

        sendButton.performClick();

        assertEquals(0, chatListView.getAdapter().getCount());
    }
}
