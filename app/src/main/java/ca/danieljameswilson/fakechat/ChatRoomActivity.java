package ca.danieljameswilson.fakechat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Daniel on 2016-11-26.
 */

public class ChatRoomActivity extends AppCompatActivity {
    private Button chat_room_a, chat_room_b;
    public Context context = this;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);
        user = getIntent().getStringExtra("username");
        chat_room_a = (Button) findViewById(R.id.chat_room_a);
        chat_room_b = (Button) findViewById(R.id.chat_room_b);
        chat_room_a.setOnClickListener(chatRoomAListener);
        chat_room_b.setOnClickListener(chatRoomBListener);
    }

    private View.OnClickListener chatRoomAListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectChatroom('A');
        }
    };
    private View.OnClickListener chatRoomBListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectChatroom('B');
        }
    };
    private void selectChatroom(final char in){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        TextView text =new TextView(this);
        text.setText(String.format(getResources().getString(R.string.confirmRoom),in));
        text.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        text.setGravity(Gravity.CENTER);

        builder.setView(text);


        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, ChatActivity.class);
                int room;
                if (in == 'A'){
                    room = 0;
                }else{
                    room = 1;
                }
                intent.putExtra("chatroom", room);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
