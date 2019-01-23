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

        // Create question buttons and 'Save and Exit' button.
        buttons = new ReportButtons((Button) findViewById(R.id.whatBtn),
                (Button) findViewById(R.id.whereBtn), (Button) findViewById(R.id.whenBtn),
                (Button) findViewById(R.id.whoBtn), (Button) findViewById(R.id.detailsBtn),
                (Button) findViewById(R.id.saveBtn));

        // Create answer texts.
        editTexts = new ReportEditTexts((EditText) findViewById(R.id.whatText),
                (EditText) findViewById(R.id.whereText), (EditText) findViewById(R.id.whenText),
                (EditText) findViewById(R.id.whoText), (EditText) findViewById(R.id.detailsText));

        // Create checkboxes.
        // Checkboxes are enabled whenever a question is answered.
        checkBoxes = new ReportCheckBoxes((CheckBox) findViewById(R.id.whatCheck),
                (CheckBox) findViewById(R.id.whereCheck), (CheckBox) findViewById(R.id.whenCheck),
                (CheckBox) findViewById(R.id.whoCheck), (CheckBox) findViewById(R.id.detailsCheck));

        // Create underlines separating questions.
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
                questionClickEvent(editTexts, editTexts.whatText, checkBoxes.whatCheck,
                        buttons.saveBtn, views, views.whatView);
                break;
            case R.id.whereBtn:
                questionClickEvent(editTexts, editTexts.whereText, checkBoxes.whereCheck,
                        buttons.saveBtn, views, views.whereView);
                break;
            case R.id.whenBtn:
                questionClickEvent(editTexts, editTexts.whenText, checkBoxes.whenCheck,
                        buttons.saveBtn, views, views.whenView);
                break;
            case R.id.whoBtn:
                questionClickEvent(editTexts, editTexts.whoText, checkBoxes.whoCheck,
                        buttons.saveBtn, views, views.whoView);
                break;
            case R.id.detailsBtn:
                questionClickEvent(editTexts, editTexts.detailsText, checkBoxes.detailsCheck,
                        buttons.saveBtn, views, views.detailsView);
                break;
        }
    }

    // This is an event handler function of question click event.
    // It deals with all the actions needed on clicking a question.
    public void questionClickEvent(ReportEditTexts editTexts, EditText editText,
                                   CheckBox checkBox, Button saveBtn,
                                   ReportViews views, View view){
        enableAnswerText(editTexts, editText);
        enableBoxes(editText, checkBox, saveBtn);
        enableUnderline(views, view, editText);
    }

    // Open and collapse edit text on clicking question buttons.
    // Opens answer text view for the corresponding question, while closing other answer views.
    // Closes answer text view if it is already opened.
    protected void enableAnswerText(ReportEditTexts editTexts,
                                    TextView textView){
        if (textView.getVisibility() == View.GONE) {
            editTexts.openAndCollapse(editTexts);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    // Enable checkbox and save button when questions answered.
    protected void enableBoxes(final EditText editText, final CheckBox checkBox, final Button saveBtn){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkBox.setChecked((editText.getText().length() > 0)? true : false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkBox.setChecked((editText.getText().length() > 0)? true : false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveBtn.setEnabled(true);
            }
        });
    }

    // Set underline view to invisibility when edit text shown.
    protected void enableUnderline(ReportViews views, View view, TextView textView){
        if (textView.getVisibility() == View.VISIBLE){
            views.makeUnderline(views);
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
