package io.happybugs.happybugs.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.happybugs.happybugs.APIInterface.APIInterface;
import io.happybugs.happybugs.R;
import io.happybugs.happybugs.network.RetrofitInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        btnGoToSignUp = (Button) findViewById(R.id.button_GoToSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO(Jelldo): make counter for preventing multiple login attempts
                //Will be defined in Backend
                startSignIn();
            }
        });

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

        Retrofit rfInstance;
        rfInstance = RetrofitInstance.getInstance();
        APIInterface service = rfInstance.create(APIInterface.class);

        Call<ResponseBody> requestSignIn = service.signin(userEmail, userPwd);
        requestSignIn.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Response<ResponseBody> rb = response;
                Toast.makeText(getBaseContext(), "Login success", Toast.LENGTH_LONG).show();

                //TODO(Jelldo): get the response and show a status message
                //TODO(Jelldo): add progressbar, make async
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                //TODO(Jelldo): need to change into HomeActivity
                                startActivity(new Intent(curContext, MainActivity.class));
                                //finish();
                                //dismiss dialogs, close cursors, close search dialogs
                            }
                        }, 3000);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Network Failure
                Toast.makeText(getBaseContext(), "Login failed due to network error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public boolean isValidSignInForm() {
        boolean isValid = true;

        String userEmail = etuserEmail.getText().toString();
        String userPwd = etuserPwd.getText().toString();

        if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etuserEmail.setError("Enter valid email address");
            isValid = false;
        } else {
            etuserEmail.setError(null);
        }

        if (userPwd.isEmpty() || userPwd.length() < 8) {
            etuserPwd.setError("Longer than alphanumeric characters");
            isValid = false;
        } else {
            etuserPwd.setError(null);
        }
        return isValid;
    }
}
