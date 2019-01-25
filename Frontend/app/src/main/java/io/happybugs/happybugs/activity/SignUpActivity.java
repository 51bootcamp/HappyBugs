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
    private EditText etRegPW;
    private EditText etRegPWCheck;
    private String userEmail;
    private String userPW;
    private String userPWCheck;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        curContext = this;
        etRegEmail = (EditText) findViewById(R.id.editText_reg_email);
        etRegPW = (EditText) findViewById(R.id.editText_reg_pw);
        etRegPWCheck = (EditText) findViewById(R.id.editText_reg_pw_check);
        btnSignUp = (Button) findViewById(R.id.button_signup);

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
        userPW = etRegPW.getText().toString();
        userPWCheck = etRegPWCheck.getText().toString();

        //Check email format
        if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etRegEmail.setError("Enter valid email address");
            isValid = false;
        } else {
            etRegEmail.setError(null);
        }

        if (userPW.isEmpty() || userPW.length() < 6 || userPW.length() > 16) {
            etRegPW.setError("Between 6 and 16 alphanumeric characters");
            isValid = false;
        } else {
            etRegPW.setError(null);
        }

        //Check password twice
        if (!userPWCheck.equals(userPW)) {
            /* INVALID PASSWORD */
            etRegPWCheck.setError("Passwords must match");
            isValid = false;
        } else {
            /* SUCCESSFUL PASSWORD CHECK */
            etRegPWCheck.setError(null);
        }
        return isValid;
    }

    private void regUserInfo() {
        userEmail = etRegEmail.getText().toString();
        userPW = etRegPW.getText().toString();
        //TODO(Jelldo): register userData
    }

    public void buttonSignUp(View v) {
        //TODO(Jelldo): Define button action.
    }

}