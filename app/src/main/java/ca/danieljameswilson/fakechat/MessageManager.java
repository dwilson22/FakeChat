package ca.danieljameswilson.fakechat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.danieljameswilson.fakechat.domain.DatabaseHelper;

/**
 * Created by Daniel on 2016-12-01.
 */

public class MessageManager {

    private DatabaseHelper dbhelper;

    public MessageManager(Context context) {
        dbhelper = DatabaseHelper.getInstance(context);
    }

    public List<Message> getMessages() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME_MSGS, null);
        List<Message> list = new ArrayList<>();


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Message item = new Message(
                        cursor.getString(cursor.getColumnIndex("message")),
                        cursor.getString(cursor.getColumnIndex("user")),
                        cursor.getInt(cursor.getColumnIndex("chatroom")));
                list.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return list;
    }

    public void add(Message msg) {

        ContentValues newMessage = new ContentValues();
        newMessage.put("message", msg.getMessage());
        newMessage.put("user", msg.getUser());
        newMessage.put("chatroom", msg.getChatroom());

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.insert(DatabaseHelper.TABLE_NAME_MSGS, null, newMessage);
    }
}
