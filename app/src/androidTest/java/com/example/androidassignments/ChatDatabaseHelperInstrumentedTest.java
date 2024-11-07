package com.example.androidassignments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ChatDatabaseHelperInstrumentedTest {

    private ChatDatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Before
    public void initializeDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        databaseHelper = new ChatDatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    @After
    public void closeDatabase() {
        database.close();
        databaseHelper.close();
    }

    @Test
    public void verifyDatabaseOpens() {
        assertTrue(database.isOpen());
    }

    @Test
    public void validateTableStructure() {
        Cursor cursor = database.rawQuery("PRAGMA table_info(" + ChatDatabaseHelper.TABLE_NAME + ")", null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());

        boolean hasPrimaryIdColumn = false;
        boolean hasMessageTextColumn = false;

        do {
            String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            if (columnName.equals(ChatDatabaseHelper.KEY_ID)) {
                hasPrimaryIdColumn = true;
            } else if (columnName.equals(ChatDatabaseHelper.KEY_MESSAGE)) {
                hasMessageTextColumn = true;
            }
        } while (cursor.moveToNext());

        cursor.close();

        assertTrue(hasPrimaryIdColumn);
        assertTrue(hasMessageTextColumn);
    }

    @Test
    public void ensureDatabaseUpgrade() {
        databaseHelper.onUpgrade(database, 1, 2);

        Cursor cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        cursor.close();
    }
}
