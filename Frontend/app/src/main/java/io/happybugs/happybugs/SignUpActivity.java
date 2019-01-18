package io.happybugs.happybugs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    EditText etRegId;
    EditText etRegPw;
    EditText etRegPwcheck;
    String userEmail;
    String userPw;
    String userPwcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etRegId = (EditText) findViewById(R.id.etRegEmail);
        etRegPw = (EditText) findViewById(R.id.etRegPw);
        etRegPwcheck = (EditText) findViewById(R.id.etRegPwcheck);

        //TODO(Jelldo): homeIntent is needed
    }

    private void RegisterUser(String email, String password) {
        //TODO(Jelldo)
    }

    public void buttonSignUp(View view) {
        userEmail = etRegId.getText().toString();
        userPw = etRegPw.getText().toString();
        userPwcheck = etRegPwcheck.getText().toString();

        if (userPw.equals(userPwcheck)) {
            /* SUCCESSFUL PASSWORD CHECK */
        } else {
            /* INVALID PASSWORD */
        }
    }

}
