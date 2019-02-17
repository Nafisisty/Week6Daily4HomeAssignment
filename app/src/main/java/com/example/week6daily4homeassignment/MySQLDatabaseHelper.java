package com.example.week6daily4homeassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import static com.example.week6daily4homeassignment.DatabaseConstants.DATABASE_NAME;
import static com.example.week6daily4homeassignment.DatabaseConstants.DATABASE_VERSION;
import static com.example.week6daily4homeassignment.DatabaseConstants.FIELD_NAME;
import static com.example.week6daily4homeassignment.DatabaseConstants.FIELD_PASSWORD;
import static com.example.week6daily4homeassignment.DatabaseConstants.TABLE_NAME;

public class MySQLDatabaseHelper extends SQLiteOpenHelper {

    EncryptDecryptDataUtil encryptDecryptDataUtil;

    public MySQLDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        encryptDecryptDataUtil = new EncryptDecryptDataUtil(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + FIELD_NAME + " TEXT PRIMARY KEY, "
                + FIELD_PASSWORD + " TEXT)";

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(user != null) {

            contentValues.put(FIELD_NAME, user.getUserName());
            contentValues.put(FIELD_PASSWORD, encryptDecryptDataUtil.dataEncrption(user.getPassword()));

            database.insert(TABLE_NAME, null, contentValues);
        }
    }

    public User getUserByName(String userName) {

        User user= null;

        if(userName != null && !userName.isEmpty()) {

            SQLiteDatabase database = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME
                    + " WHERE " + FIELD_NAME + " = \"" + userName + "\"";

            Cursor cursor = database.rawQuery(query, null);
            if(cursor.moveToNext()) {

                String name = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
                String pass = cursor.getString(cursor.getColumnIndex(FIELD_PASSWORD));

                user = new User(name, encryptDecryptDataUtil.dataDecrytion(pass));

            }
            cursor.close();

        }
        return user;
    }

    public int updateUser(User user) {
        if(user != null) {
            String whereClause = FIELD_NAME + " = \"" + user.getUserName() + "\"";
            SQLiteDatabase database = getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put(FIELD_NAME, user.getUserName());
            contentValues.put(FIELD_PASSWORD, encryptDecryptDataUtil.dataEncrption(user.getPassword()));

            return database.update(TABLE_NAME, contentValues, whereClause, null);

        }
        return 0;
    }
}
