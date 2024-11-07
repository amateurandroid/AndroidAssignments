package com.example.androidassignments;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.test.core.app.ApplicationProvider;

import com.example.androidassignments.ChatDatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class ChatDatabaseHelperTest {

    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dbHelper = new ChatDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @Test
    public void testDatabaseCreation() {
        assertTrue(db.isOpen());

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);
        assertTrue("Messages table not created", cursor.moveToFirst());
        cursor.close();
    }
    @Test
    public void testTableColumns() {
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + ChatDatabaseHelper.TABLE_NAME + ");", null);

        boolean hasIdColumn = false;
        boolean hasMessageColumn = false;

        while (cursor.moveToNext()) {
            String columnName = cursor.getString(cursor.getColumnIndex("name"));
            if (ChatDatabaseHelper.KEY_ID.equals(columnName)) hasIdColumn = true;
            if (ChatDatabaseHelper.KEY_MESSAGE.equals(columnName)) hasMessageColumn = true;
        }
        cursor.close();

        assertTrue("id column missing", hasIdColumn);
        assertTrue("message column missing", hasMessageColumn);
    }
    @Test
    public void testDatabaseUpgrade() {
        db.execSQL("INSERT INTO " + ChatDatabaseHelper.TABLE_NAME + " (" + ChatDatabaseHelper.KEY_MESSAGE + ") VALUES ('Test message');");

        dbHelper.onUpgrade(db, 1, 2);

        Cursor cursor = db.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME, null);
        assertEquals("Table not cleared during upgrade", 0, cursor.getCount());
        cursor.close();
    }
    @Test
    public void testDatabaseVersion() {
        int expectedVersion = 2;
        assertEquals("Database version mismatch", expectedVersion, db.getVersion());
    }

}