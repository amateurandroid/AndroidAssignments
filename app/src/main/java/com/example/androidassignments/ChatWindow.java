package com.example.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import android.os.AsyncTask;
import android.widget.Toast;


public class ChatWindow extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "ChatWindow";
    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;
    private ArrayList<String> chatMessages;
    private ChatAdapter chatAdapter;
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatListView = findViewById(R.id.chatListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        chatMessages = new ArrayList<>();

        chatAdapter = new ChatAdapter(ChatWindow.this, chatMessages);
        chatListView.setAdapter(chatAdapter);

        dbHelper = new ChatDatabaseHelper(this);

        db = dbHelper.getWritableDatabase();

        new LoadMessagesTask().execute();

        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!message.isEmpty()) {
                new SendMessageTask().execute(message);
            }
        });
    }

    private class LoadMessagesTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> messages = new ArrayList<>();
            Cursor cursor = db.query(
                    ChatDatabaseHelper.TABLE_NAME,
                    new String[]{ChatDatabaseHelper.KEY_MESSAGE},
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int messageColumnIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
                    if (messageColumnIndex != -1) {
                        String message = cursor.getString(messageColumnIndex);
                        messages.add(message);
                    }
                }
                cursor.close();
            }
            return messages;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            chatMessages.addAll(result);
            chatAdapter.notifyDataSetChanged();
        }
    }

    private class SendMessageTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String message = params[0];

            ContentValues values = new ContentValues();
            values.put(ChatDatabaseHelper.KEY_MESSAGE, message); // Insert message
            db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String message = messageEditText.getText().toString().trim();
            chatMessages.add(message);
            chatAdapter.notifyDataSetChanged();

            messageEditText.setText("");

            Toast.makeText(ChatWindow.this, "Message sent!", Toast.LENGTH_SHORT).show();
        }
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx, ArrayList<String> chatMessages) {
            super(ctx, 0, chatMessages);
        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public String getItem(int position) {
            return chatMessages.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView getMessage = result.findViewById(R.id.message_text);
            getMessage.setText(getItem(position));

            return result;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
