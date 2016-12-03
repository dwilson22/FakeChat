package ca.danieljameswilson.fakechat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Daniel on 2016-11-29.
 */

public class ChatActivity extends AppCompatActivity {
    private MessageManager messageManager;
    private MessageAdapter adapter;
    private String user;
    private int chatroom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        user = getIntent().getStringExtra("user");
        chatroom = getIntent().getIntExtra("chatroom", 0);
        ListView Messages = (ListView) findViewById(R.id.chat_screen);
        messageManager = new MessageManager(getApplicationContext());
        adapter = new MessageAdapter(this, messageManager.getMessages(chatroom));

        Messages.setAdapter(adapter);

        final EditText msgEditText = (EditText) findViewById(R.id.send);
        ImageButton addItem = (ImageButton) findViewById(R.id.send_message);
        addItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!msgEditText.getText().toString().isEmpty()) {
                    Message msg = new Message(msgEditText.getText().toString(), user, chatroom);
                    messageManager.add(msg);
                    adapter.swapList(messageManager.getMessages(chatroom));
                    msgEditText.setText("");
                    hideKeybord(v);
                }else{
                    CharSequence text = ChatActivity.this.getResources().getString(R.string.empty_msg);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ChatActivity.this, text, duration);
                    toast.show();
                }
            }
        });
    }

    public void hideKeybord(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    protected class MessageAdapter extends ArrayAdapter<Message> {
        private Context context;
        private List<Message> msg;
        private LayoutInflater inflater;

        public MessageAdapter(Context context, List<Message> msg){
            super(context, -1, msg);
            this.context = context;
            this.msg = msg;
            this.inflater = LayoutInflater.from(context);
        }

        public void swapList(List<Message> msg){
            this.msg = msg;
            notifyDataSetChanged();
        }

        @Override
        public int getCount(){
            return msg.size();
        }

        @Override
        public View getView(int position, View  convertView, ViewGroup parent) {
            final MessageViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.message, parent, false);
                holder = new MessageViewHolder();
                holder.user = (TextView) convertView.findViewById(R.id.user);
                holder.message = (TextView) convertView.findViewById(R.id.message);
                convertView.setTag(holder);
            }else{
                holder = (MessageViewHolder) convertView.getTag();
            }

            if(msg.get(position).getUser().equals(user)) {
                holder.message.setBackground(ContextCompat.getDrawable(context, R.drawable.round_messge_user));
            }else{
                holder.message.setBackground(ContextCompat.getDrawable(context, R.drawable.round_message));
            }
            holder.message.setPadding(25,25,25,25);

            holder.user.setText(msg.get(position).getUser());
            holder.message.setText(msg.get(position).getMessage());

            return convertView;
        }
    }

    public static class MessageViewHolder{
        public TextView user;
        public TextView message;
    }
}
