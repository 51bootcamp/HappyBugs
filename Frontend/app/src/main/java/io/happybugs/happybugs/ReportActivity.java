package io.happybugs.happybugs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener{
    ReportButtons buttons;
    ReportEditTexts editTexts;
    ReportCheckBoxes checkBoxes;
    ReportViews views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        buttons = new ReportButtons((Button) findViewById(R.id.whatBtn),
                (Button) findViewById(R.id.whereBtn), (Button) findViewById(R.id.whenBtn),
                (Button) findViewById(R.id.whoBtn), (Button) findViewById(R.id.detailsBtn),
                (Button) findViewById(R.id.saveBtn));

        editTexts = new ReportEditTexts((EditText) findViewById(R.id.whatText),
                (EditText) findViewById(R.id.whereText), (EditText) findViewById(R.id.whenText),
                (EditText) findViewById(R.id.whoText), (EditText) findViewById(R.id.detailsText));

        checkBoxes = new ReportCheckBoxes((CheckBox) findViewById(R.id.whatCheck),
                (CheckBox) findViewById(R.id.whereCheck), (CheckBox) findViewById(R.id.whenCheck),
                (CheckBox) findViewById(R.id.whoCheck), (CheckBox) findViewById(R.id.detailsCheck));

        views = new ReportViews((View) findViewById(R.id.whatView), (View) findViewById(R.id.whereView),
                (View) findViewById(R.id.whenView), (View) findViewById(R.id.whoView),
                (View) findViewById(R.id.detailsView));

        buttons.whatBtn.setOnClickListener(this);
        buttons.whereBtn.setOnClickListener(this);
        buttons.whenBtn.setOnClickListener(this);
        buttons.whoBtn.setOnClickListener(this);
        buttons.detailsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.whatBtn:
                enableTextView(editTexts.whatText, editTexts.whereText,
                        editTexts.whenText, editTexts.whoText, editTexts.detailsText);
                enableBoxes(editTexts.whatText, checkBoxes.whatCheck, buttons.saveBtn);
                enableView(editTexts.whatText, views.whatView,
                        views.whereView, views.whenView, views.whoView, views.detailsView);
                break;
            case R.id.whereBtn:
                enableTextView(editTexts.whereText, editTexts.whatText,
                        editTexts.whenText, editTexts.whoText, editTexts.detailsText);
                enableBoxes(editTexts.whereText, checkBoxes.whereCheck, buttons.saveBtn);
                enableView(editTexts.whereText, views.whereView,
                        views.whatView, views.whenView, views.whoView, views.detailsView);
                break;
            case R.id.whenBtn:
                enableTextView(editTexts.whenText, editTexts.whatText,
                        editTexts.whereText, editTexts.whoText, editTexts.detailsText);
                enableBoxes(editTexts.whenText, checkBoxes.whenCheck, buttons.saveBtn);
                enableView(editTexts.whenText, views.whenView,
                        views.whatView, views.whereView, views.whoView, views.detailsView);
                break;
            case R.id.whoBtn:
                enableTextView(editTexts.whoText, editTexts.whatText,
                        editTexts.whereText, editTexts.whenText, editTexts.detailsText);
                enableBoxes(editTexts.whoText, checkBoxes.whoCheck,buttons.saveBtn);
                enableView(editTexts.whoText, views.whoView,
                        views.whatView, views.whereView, views.whenView, views.detailsView);
                break;
            case R.id.detailsBtn:
                enableTextView(editTexts.detailsText, editTexts.whatText,
                        editTexts.whereText, editTexts.whenText, editTexts.whoText);
                enableBoxes(editTexts.detailsText, checkBoxes.detailsCheck, buttons.saveBtn);
                enableView(editTexts.detailsText, views.detailsView,
                        views.whatView, views.whereView, views.whenView, views.whoView);
                break;
        }
    }

    //Open and collapse edit text on button click
    protected void enableTextView(TextView tv1, TextView tv2,
                                  TextView tv3, TextView tv4, TextView tv5){
        if (tv1.getVisibility() == View.GONE) {
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            tv5.setVisibility(View.GONE);
            tv1.setVisibility(View.VISIBLE);
        } else {
            tv1.setVisibility(View.GONE);
        }
    }

    //Enable checkbox and save button when questions answered
    protected void enableBoxes(final EditText et, final CheckBox cb, final Button saveBtn){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (et.getText().length() > 0){
                    cb.setChecked(true);
                } else {
                    cb.setChecked(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et.getText().length() > 0){
                    cb.setChecked(true);
                } else {
                    cb.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveBtn.setEnabled(true);
            }
        });
    }

    //Set underline view to invisibility when edit text shown
    protected void enableView(TextView tv, View v1, View v2, View v3, View v4, View v5){
        if (tv.getVisibility() == View.VISIBLE){
            v1.setVisibility(View.INVISIBLE);
            v2.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            v4.setVisibility(View.VISIBLE);
            v5.setVisibility(View.VISIBLE);
        } else {
            v1.setVisibility(View.VISIBLE);
        }
    }
}
