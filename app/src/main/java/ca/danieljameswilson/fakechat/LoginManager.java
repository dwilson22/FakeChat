package ca.danieljameswilson.fakechat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.danieljameswilson.fakechat.domain.DatabaseHelper;

/**
 * Created by Daniel on 2016-11-26.
 */

public class LoginManager {
    public String username;
    private String password;
    private DatabaseHelper dbHelper;

    public LoginManager(String username, String password, Context context){
        this.username=username;
        this.password=password;
        dbHelper = DatabaseHelper.getInstance(context);
    }
    public LoginManager( Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public boolean isLoginSuccessful(){
        boolean found = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME_USERS, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(cursor.getString(cursor.getColumnIndex("username")).equals(username) &&
                   cursor.getString(cursor.getColumnIndex("password")).equals(password)){
                    found = true;
                }

                cursor.moveToNext();
            }
        }


        cursor.close();
        return found;
    }

    public boolean register(String username, String password){
        boolean registered = true;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME_USERS, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(cursor.getString(cursor.getColumnIndex("username")).equals(username) &&
                        cursor.getString(cursor.getColumnIndex("password")).equals(password)){
                    registered = false;
                }

                cursor.moveToNext();
            }
        }
        cursor.close();

        if(registered){
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            db.insert(DatabaseHelper.TABLE_NAME_USERS, null,values);
        }

        return registered;
    }
}
