package ca.danieljameswilson.fakechat.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daniel on 2016-11-26.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fakechat.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_USERS = "users";
    public static final String TABLE_NAME_MSGS = "msgs";
    private static DatabaseHelper instance = null;

    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            Log.d("DATABASE INFO", "DATABASE IS CREATED");
            instance = new DatabaseHelper(context);
        }
        return instance;
    }
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME_USERS +" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL,"+
                "password TEXT NOT NULL,"+
                "isLoggedIn INTEGER NOT NULL DEFAULT 0)";

        db.execSQL(createQuery);

         createQuery = "CREATE TABLE " + TABLE_NAME_MSGS +" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "description TEXT NOT NULL,"+
                "user TEXT NOT NULL,"+
                "received INTEGER NOT NULL DEFAULT 0)";

        db.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}