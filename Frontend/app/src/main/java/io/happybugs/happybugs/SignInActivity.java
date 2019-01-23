package io.happybugs.happybugs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private Context curContext;
    private Button btnGoToSignUp;
    private Button btnSignIn;

    private EditText etuserEmail;
    private EditText etuserPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        curContext = this;

        etuserEmail = (EditText) findViewById(R.id.editText_userEmail);
        etuserPwd = (EditText) findViewById(R.id.editText_userPwd);
        btnSignIn = (Button) findViewById(R.id.button_SignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO(Jelldo): make counter for preventing multiple login attempts
                startSignIn();
            }
        });
        btnGoToSignUp = (Button) findViewById(R.id.button_GoToSignUp);
        btnGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(curContext, SignUpActivity.class));
            }
        });
    }

    public void startSignIn() {
        if (!isValidSignInForm()) {
            Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
            btnSignIn.setEnabled(true);
            return;
        }
        btnSignIn.setEnabled(false);

        String userEmail = etuserEmail.getText().toString();
        String userPwd = etuserPwd.getText().toString();
        //TODO(Jelldo): send email,pw to server / add authenticate

        //TODO(Jelldo): add progressbar, make async
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        //TODO(Jelldo): need to change into HomeActivity
                        startActivity(new Intent(curContext, MainActivity.class));
                        //finish();
                        // dismiss dialogs, close cursors, close search dialogs
                    }
                }, 3000);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public boolean isValidSignInForm() {
        boolean valid = true;

        String userEmail = etuserEmail.getText().toString();
        String userPwd = etuserPwd.getText().toString();

        if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etuserEmail.setError("Enter valid email address");
            valid = false;
        } else {
            etuserEmail.setError(null);
        }

        if (userPwd.isEmpty() || userPwd.length() < 6 || userPwd.length() > 16) {
            etuserPwd.setError("Between 6 and 16 alphanumeric characters");
            valid = false;
        } else {
            etuserPwd.setError(null);
        }
        return valid;
    }
}
