package com.example.androidassignments;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    ArrayList<String> chatMessages;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_window);

        //initializing the variables
        chatListView = findViewById(R.id.chatListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        chatMessages = new ArrayList<>();

        //initializing the ChatAdapter and set it to the ListView
        chatAdapter = new ChatAdapter(ChatWindow.this);
        chatListView.setAdapter(chatAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set onClickListener for the Send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString().trim();
                if (!message.isEmpty()) {
                    chatMessages.add(message);
                    messageEditText.setText("");
                    chatAdapter.notifyDataSetChanged(); //notifying adapter data changed

            }
         }
        });
    }

    //inner class extending ArrayAdapter<String>
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0, chatMessages);

        }

        //returns the chat message at a specific position in the list
        @Override
        public int getCount() {
            return chatMessages.size();
        }

        public String getItem(int position) {
            return chatMessages.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Create a LayoutInflater object
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result;

            // Check if the position is even or odd
            if (position % 2 == 0) {
                // Inflate the incoming message layout for even positions
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                // Inflate the outgoing message layout for odd positions
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            // Get the TextView from the inflated layout
            TextView getMessage = (TextView)result.findViewById(R.id.message_text);
            getMessage.setText(getItem(position));

            // Set the text for the TextView with the message at the specified position

            // Return the view to be displayed in the ListView
            return result;
        };


    }
}
