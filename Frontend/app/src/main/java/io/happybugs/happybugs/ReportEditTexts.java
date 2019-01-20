package io.happybugs.happybugs;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class ReportEditTexts extends AppCompatActivity {
    EditText whatText;
    EditText whereText;
    EditText whenText;
    EditText whoText;
    EditText detailsText;

    public ReportEditTexts(EditText whatText, EditText whereText,
                     EditText whenText, EditText whoText, EditText detailsText) {
        this.whatText = whatText;
        this.whereText = whereText;
        this.whenText = whenText;
        this.whoText = whoText;
        this.detailsText = detailsText;
    }

    public EditText getWhatText() {
        return whatText;
    }

    public EditText getWhereText() {
        return whereText;
    }

    public EditText getWhenText() {
        return whenText;
    }

    public EditText getWhoText() {
        return whoText;
    }

    public EditText getDetailsText() {
        return detailsText;
    }
}
