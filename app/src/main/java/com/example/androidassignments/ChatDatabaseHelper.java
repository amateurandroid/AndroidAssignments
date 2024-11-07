package com.example.androidassignments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 4;

    public static final String TABLE_NAME = "Messages";
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MESSAGE + " TEXT" +
                ");";

        db.execSQL(CREATE_TABLE);
        Log.i("ChatDatabaseHelper", "Table " + TABLE_NAME + " created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertMessage(String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Log.e("ChatDatabaseHelper", "Failed to insert message into database.");
        } else {
            Log.i("ChatDatabaseHelper", "Message inserted into database: " + message);
        }

        return result;
    }

    public Cursor getMessages() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{KEY_MESSAGE}, null, null, null, null, null);
    }
}
