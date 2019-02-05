package io.happybugs.happybugs.activity;

import android.content.ContentUris;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReportEditTexts extends AppCompatActivity {
    CustomEditText whatText;
    CustomEditText whereText;
    CustomEditText whenText;
    CustomEditText whoText;
    EditText facebookIDText;
    CustomEditText detailsText;

    public ReportEditTexts(CustomEditText whatText, CustomEditText whereText, CustomEditText whenText,
                           CustomEditText whoText, EditText facebookIDText, CustomEditText detailsText) {
        this.whatText = whatText;
        this.whereText = whereText;
        this.whenText = whenText;
        this.whoText = whoText;
        this.facebookIDText = facebookIDText;
        this.detailsText = detailsText;
    }

    public String getWhatText() {
        return whatText.getText().toString();
    }

    public String getWhereText() {
        return whereText.getText().toString();
    }

    public String getWhenText() {
        return whenText.getText().toString();
    }

    public String getWhoText() {
        return whoText.getText().toString();
    }

    public String getFacebookIDText(){
        String uniqueURL = facebookIDText.getText().toString();
        return uniqueURL.substring(25);
    }

    public String getDetailsText() {
        return detailsText.getText().toString();
    }

    // Sets edit texts to invisibility.
    // Used when opening and collapsing answer texts on question click events.
    public void openAndCollapse(ReportEditTexts editTexts){
        editTexts.whatText.setVisibility(View.GONE);
        editTexts.whereText.setVisibility(View.GONE);
        editTexts.whenText.setVisibility(View.GONE);
        editTexts.whoText.setVisibility(View.GONE);
        editTexts.facebookIDText.setVisibility(View.GONE);
        editTexts.detailsText.setVisibility(View.GONE);
    }

    public void onTextTouched(EditText editText, final Button button){
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
