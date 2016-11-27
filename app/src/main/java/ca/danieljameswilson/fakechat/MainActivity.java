package ca.danieljameswilson.fakechat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private int attempts;
    private Button loginButton, registerButton;
    private EditText passwordEditText;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(onClickLogin);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(onClickRegister);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        attempts = 3;

        passwordEditText.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            loginButton.performClick();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
            // TODO Auto-generated method stub

        });
    }

    private View.OnClickListener onClickRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClickRegisterUsername();
        }
    };
    private View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            EditText userNameEditText = (EditText) findViewById(R.id.usernameEditText);
            TextView errorTextView = (TextView) findViewById(R.id.errorTextView);

            String username = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            LoginManager loginManager = new LoginManager(username, password, getApplicationContext());

            if(loginManager.isLoginSuccessful()){
                //success
                attempts = 3;
                errorTextView.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(context, ChatRoomActivity.class);
                startActivity(intent);


            }else{
                //failure
                attempts--;
                String errormsg = getString(R.string.errorMsg)+String.valueOf(attempts)+getString(R.string.attempts);
                errorTextView.setText(errormsg);
                errorTextView.setVisibility(View.VISIBLE);
                if(attempts==0){
                    loginButton.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    public void onClickRegisterUsername(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.register);
        final EditText user = new EditText(MainActivity.this);
        final EditText pass = new EditText(MainActivity.this);
        LinearLayout layout = new LinearLayout(MainActivity.this);

        user.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        user.setHint(R.string.loginHint);
        pass.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        pass.setHint(R.string.passwordHint);
        pass.setHintTextColor(ContextCompat.getColor(MainActivity.this, R.color.hint));
        user.setHintTextColor(ContextCompat.getColor(MainActivity.this, R.color.hint));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(user);
        layout.addView(pass);
        builder.setView(layout);

        builder.setPositiveButton(R.string.register_next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager LM = new LoginManager(MainActivity.this);
                if(LM.register(user.getText().toString(), pass.getText().toString())){
                    CharSequence text = MainActivity.this.getResources().getString(R.string.succesful);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                }else{
                    CharSequence text = MainActivity.this.getResources().getString(R.string.failed);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                }
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
