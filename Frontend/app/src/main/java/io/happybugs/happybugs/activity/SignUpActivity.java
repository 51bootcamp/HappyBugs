package io.happybugs.happybugs.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.happybugs.happybugs.R;

public class SignUpActivity extends AppCompatActivity {

    private Context curContext;
    private EditText etRegEmail;
    private EditText etRegPw;
    private EditText etRegPwcheck;
    private String userEmail;
    private String userPw;
    private String userPwcheck;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        curContext = this;
        etRegEmail = (EditText) findViewById(R.id.editText_RegEmail);
        etRegPw = (EditText) findViewById(R.id.editText_RegPwcheck);
        etRegPwcheck = (EditText) findViewById(R.id.editText_RegPwcheck);
        btnSignUp = (Button) findViewById(R.id.button_SignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO(Jelldo): open HomeActivity if get success register
                if (isValidSignUpForm()) {
                    //TODO(Jelldo): if true -> register and open HomeActivity
                    regUserInfo();
                    startActivity(new Intent(curContext, MainActivity.class));
                    //TODO(Jelldo): need to kill SignUpActivity when HomeActivity is opened
                }
            }
        });
    }

    private boolean isValidSignUpForm() {
        boolean isValid = true;

        userEmail = etRegEmail.getText().toString();
        userPw = etRegPw.getText().toString();
        userPwcheck = etRegPwcheck.getText().toString();

        //Check email format
        if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etRegEmail.setError("Enter valid email address");
            isValid = false;
        } else {
            etRegEmail.setError(null);
        }

        if (userPw.isEmpty() || userPw.length() < 6 || userPw.length() > 16) {
            etRegPw.setError("Between 6 and 16 alphanumeric characters");
            isValid = false;
        } else {
            etRegPw.setError(null);
        }

        //Check password twice
        if (!userPwcheck.equals(userPw)) {
            /* INVALID PASSWORD */
            etRegPwcheck.setError("Passwords must match");
            isValid = false;
        } else {
            /* SUCCESSFUL PASSWORD CHECK */
            etRegPwcheck.setError(null);
        }
        return isValid;
    }

    private void regUserInfo() {
        userEmail = etRegEmail.getText().toString();
        userPw = etRegPw.getText().toString();
        //TODO(Jelldo): register userData
    }

}