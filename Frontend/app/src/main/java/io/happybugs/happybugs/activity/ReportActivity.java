package io.happybugs.happybugs.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.happybugs.happybugs.APIInterface.APIInterface;
import io.happybugs.happybugs.R;
import io.happybugs.happybugs.model.UserReportItem;
import io.happybugs.happybugs.model.UserReportList;
import io.happybugs.happybugs.network.RetrofitInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {
    ReportButtons buttons;
    ReportEditTexts editTexts;
    ReportCheckBoxes checkBoxes;
    ReportViews views;
    Context currContext;
    InputMethodManager imm;
    Call<UserReportList> requestReportList;
    Boolean isFromBtnEditReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        currContext = this;
        isFromBtnEditReport = false;
        // Create question buttons and 'Save and Exit' button.
        buttons = new ReportButtons((Button) findViewById(R.id.whatBtn),
                (Button) findViewById(R.id.whereBtn), (Button) findViewById(R.id.whenBtn),
                (Button) findViewById(R.id.whoBtn), (Button) findViewById(R.id.detailsBtn),
                (Button) findViewById(R.id.saveBtn),
                (ImageButton) findViewById(R.id.close_report_act));

        // Create answer texts.
        editTexts = new ReportEditTexts((CustomEditText) findViewById(R.id.whatText),
                (CustomEditText) findViewById(R.id.whereText), (CustomEditText) findViewById(R.id.whenText),
                (CustomEditText) findViewById(R.id.whoText),
                (EditText) findViewById(R.id.facebook_edit_text),
                (CustomEditText) findViewById(R.id.detailsText));

        // Create checkboxes.
        // Checkboxes are enabled whenever a question is answered.
        checkBoxes = new ReportCheckBoxes((CheckBox) findViewById(R.id.whatCheck),
                (CheckBox) findViewById(R.id.whereCheck), (CheckBox) findViewById(R.id.whenCheck),
                (CheckBox) findViewById(R.id.whoCheck), (CheckBox) findViewById(R.id.detailsCheck));

        // Create underlines separating questions.
        views = new ReportViews((View) findViewById(R.id.whatView), (View) findViewById(R.id.whereView),
                (View) findViewById(R.id.whenView), (View) findViewById(R.id.whoView),
                (View) findViewById(R.id.facebook_text_input),
                (View) findViewById(R.id.detailsView));

        imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);

        startReport(editTexts.whatText, views.whatView, buttons.saveBtn);

        buttons.whatBtn.setOnClickListener(this);
        buttons.whereBtn.setOnClickListener(this);
        buttons.whenBtn.setOnClickListener(this);
        buttons.whoBtn.setOnClickListener(this);
        buttons.detailsBtn.setOnClickListener(this);
        buttons.saveBtn.setOnClickListener(this);
        buttons.closeBtn.setOnClickListener(this);

        onKeyboardDownPressed();
        textTouchListener();

        Retrofit rfInstance = RetrofitInstance.getInstance(currContext);
        APIInterface service = rfInstance.create(APIInterface.class);

        try {
            isFromBtnEditReport = getIntent().getExtras().getBoolean("isFromBtnEditReport");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (isFromBtnEditReport) {
            final int reportID = getIntent().getExtras().getInt("reportID");
            requestReportList = service.findReport(reportID);
            requestReportList.enqueue(new Callback<UserReportList>() {
                @Override
                public void onResponse(Call<UserReportList> call, Response<UserReportList> response) {
                    if (response.code() == 200) {
                        List<UserReportItem> userReportItems = response.body().getData();
                        editTexts.whatText.setText(userReportItems.get(0).getWhat());
                        editTexts.whereText.setText(userReportItems.get(0).getLocation());
                        editTexts.whenText.setText(userReportItems.get(0).getTime());
                        editTexts.whoText.setText(userReportItems.get(0).getWho());
                        editTexts.detailsText.setText(userReportItems.get(0).getDetails());
                        //TODO(Jelldo): Set facebookIDtext without using String fb concat if it is available
                        final String fb = "https://www.facebook.com/";
                        editTexts.facebookIDText.setText(fb.concat(userReportItems.get(0).getPerpetrator().getFacebookUrl()));
                    } else if (response.code() == 403) {
                        Toast.makeText(getBaseContext(), "Editing report failed due to session expiration",
                                Toast.LENGTH_LONG).show();
                    } else if (response.code() == 400) {
                        //reportID != Integer
                    }
                }

                @Override
                public void onFailure(Call<UserReportList> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "Editing report failed due to network error",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.whatBtn:
                questionClickEvent(editTexts, editTexts.whatText, checkBoxes.whatCheck,
                        buttons.saveBtn, views, views.whatView, views.facebookView);
                break;
            case R.id.whereBtn:
                questionClickEvent(editTexts, editTexts.whereText, checkBoxes.whereCheck,
                        buttons.saveBtn, views, views.whereView, views.facebookView);
                break;
            case R.id.whenBtn:
                questionClickEvent(editTexts, editTexts.whenText, checkBoxes.whenCheck,
                        buttons.saveBtn, views, views.whenView, views.facebookView);
                break;
            case R.id.whoBtn:
                enableAnswerText(editTexts, editTexts.whoText, views.facebookView);
                enableUnderline(views, views.whoView, editTexts.whoText);

                editTexts.whoText.addTextChangedListener(new WhoTextChange(editTexts.whoText,
                        editTexts.facebookIDText, editTexts.whoText,
                        checkBoxes.whoCheck, buttons.saveBtn));
                editTexts.facebookIDText.addTextChangedListener(new WhoTextChange(editTexts.whoText,
                        editTexts.facebookIDText, editTexts.whoText,
                        checkBoxes.whoCheck, buttons.saveBtn));
                break;
            case R.id.detailsBtn:
                questionClickEvent(editTexts, editTexts.detailsText, checkBoxes.detailsCheck,
                        buttons.saveBtn, views, views.detailsView, views.facebookView);
                break;
            case R.id.saveBtn:
                if (isFromBtnEditReport) {
                    sendEditedReportData();
                } else {
                    sendCreatedReport();
                }
                break;
            case R.id.close_report_act:
                CloseReportDialog dialog = new CloseReportDialog();
                dialog.show(getSupportFragmentManager(), "close");
        }
    }

    // Start report with what question text open.
    public void startReport(EditText editText, View view, Button button) {
        editText.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
        showKeyboard(editTexts.whatText);
        enableBoxes(editTexts.whatText, checkBoxes.whatCheck, buttons.saveBtn);
    }

    // This is an event handler function of question click event.
    // It deals with all the actions needed on clicking a question.
    public void questionClickEvent(ReportEditTexts editTexts, EditText editText,
                                   CheckBox checkBox, Button saveBtn,
                                   ReportViews views, View view, View facebookView) {
        enableAnswerText(editTexts, editText, facebookView);
        enableBoxes(editText, checkBox, saveBtn);
        enableUnderline(views, view, editText);
    }

    // Open and collapse edit text on clicking question buttons.
    // Opens answer text view for the corresponding question, while closing other answer views.
    // Closes answer text view if it is already opened.
    protected void enableAnswerText(ReportEditTexts editTexts,
                                    TextView textView, View facebookView) {
        if (textView.getVisibility() == View.GONE) {
            editTexts.openAndCollapse(editTexts);
            facebookView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);

            if (textView == editTexts.whoText) {
                views.facebookView.setVisibility(View.VISIBLE);
                editTexts.facebookIDText.setVisibility(View.VISIBLE);
            }
            showKeyboard((EditText) textView);
        } else {
            textView.setVisibility(View.GONE);
            views.facebookView.setVisibility(View.GONE);
            editTexts.facebookIDText.setVisibility(View.GONE);

            if (textView == editTexts.whoText) {
                views.facebookView.setVisibility(View.GONE);
                editTexts.facebookIDText.setVisibility(View.GONE);
            }
            closeKeyboard((EditText) textView);
        }
    }

    // Enable checkbox and save button when questions answered.
    protected void enableBoxes(final EditText editText, final CheckBox checkBox, final Button saveBtn) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkBox.setChecked((editText.getText().length() > 0) ? true : false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkBox.setChecked((editText.getText().length() > 0) ? true : false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveBtn.setEnabled(true);
            }
        });
    }

    // Set underline view to invisibility when edit text shown.
    protected void enableUnderline(ReportViews views, View view, TextView textView) {
        if (textView.getVisibility() == View.VISIBLE) {
            views.makeUnderline(views);
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void showKeyboard(final EditText editText) {
        editText.requestFocus();

        if (isSoftKeyboardShown(imm, editText)) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        // Give time lapse between showing keyboard and setting save button to invisibility.
        final int DELAY_MILLIS = 50;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        buttons.saveBtn.setVisibility(View.GONE);
                    }
                }, DELAY_MILLIS);
    }

    public void closeKeyboard(EditText editText) {
        editText.clearFocus();
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        // Give time lapse between closing keyboard and setting save button to visibility.
        final int DELAY_MILLIS = 50;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        buttons.saveBtn.setVisibility(View.VISIBLE);
                    }
                }, DELAY_MILLIS);
    }

    protected boolean isSoftKeyboardShown(InputMethodManager imm, View v) {
        KeyboardActionResult result = new KeyboardActionResult();
        int res;

        imm.showSoftInput(v, 0, result);
        res = result.getResult();
        if (res == InputMethodManager.RESULT_UNCHANGED_SHOWN ||
                res == InputMethodManager.RESULT_UNCHANGED_HIDDEN) {
            return true;
        }
        return false;
    }

    // Shows save button when keyboard down button pressed.
    protected void onKeyboardDownPressed() {
        CustomEditText[] texts = {editTexts.whatText, editTexts.whereText, editTexts.whenText,
                editTexts.whoText, editTexts.detailsText};
        for (CustomEditText text : texts) {
            text.setKeyImeChangeListener(new CustomEditText.KeyImeChange() {
                @Override
                public void onKeyIme(int keyCode, KeyEvent event) {
                    if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
                        // Give time lapse between closing keyboard and setting save button to visibility.
                        final int DELAY_MILLIS = 50;
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        buttons.saveBtn.setVisibility(View.VISIBLE);
                                    }
                                }, DELAY_MILLIS);
                    }
                }
            });
        }
    }

    protected void textTouchListener(){
        editTexts.onTextTouched(editTexts.whatText, buttons.saveBtn);
        editTexts.onTextTouched(editTexts.whereText, buttons.saveBtn);
        editTexts.onTextTouched(editTexts.whenText, buttons.saveBtn);
        editTexts.onTextTouched(editTexts.whoText, buttons.saveBtn);
        editTexts.onTextTouched(editTexts.detailsText, buttons.saveBtn);
    }

    // Report saved only if user writes more than certain amount of what text.
    // Make toast if save button is not clickable.
    protected void sendCreatedReport() {
        String whatText = editTexts.whatText.getText().toString();
        if (whatText.isEmpty()) {
            buttons.saveBtn.setClickable(false);
            Toast.makeText(getBaseContext(), "Please fill out WHAT happened.",
                    Toast.LENGTH_SHORT).show();
            buttons.saveBtn.setClickable(true);
        } else if (whatText.length() < 20) {
            buttons.saveBtn.setClickable(false);
            Toast.makeText(getBaseContext(), "Please fill out more on WHAT happened.",
                    Toast.LENGTH_SHORT).show();
            buttons.saveBtn.setClickable(true);
        } else {
            buttons.saveBtn.setClickable(true);
            sendNewReportData();
        }
    }

    // POST report data to server.
    protected void sendNewReportData() {
        Retrofit rfInstance = RetrofitInstance.getInstance(currContext);
        APIInterface service = rfInstance.create(APIInterface.class);

        Call<ResponseBody> requestCreatedReport = service.createReport(userData());
        requestCreatedReport.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    startActivity(new Intent(currContext, MainActivity.class));
                } else if (response.code() == 403) {
                    Toast.makeText(getBaseContext(), "Your session has expired. Please login again.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Creating report failed due to network error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void sendEditedReportData() {
        Retrofit rfInstance = RetrofitInstance.getInstance(currContext);
        APIInterface service = rfInstance.create(APIInterface.class);

        Call<ResponseBody> requestEditReport = service.editReport(getIntent().getExtras().getInt("reportID"), userData());
        requestEditReport.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    //edit success
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                } else if (response.code() == 403) {
                    Toast.makeText(getBaseContext(), "Your session has expired. Please login again.", Toast.LENGTH_LONG).show();
                } else if (response.code() == 400) {
                    //reportID!=integer
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Editing report failed due to network error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    protected JSONObject userData() {
        JSONObject innerObj = new JSONObject();
        innerObj.put("what", editTexts.getWhatText());
        innerObj.put("location", editTexts.getWhereText());
        innerObj.put("time", editTexts.getWhenText());
        innerObj.put("who", editTexts.getWhoText());
        innerObj.put("details", editTexts.getDetailsText());
        innerObj.put("facebook_url", editTexts.getFacebookIDText());

        ArrayList<JSONObject> dataArray = new ArrayList<>();
        dataArray.add(innerObj);

        JSONObject outerObj = new JSONObject();
        outerObj.put("data", dataArray);
        return outerObj;
    }
}
