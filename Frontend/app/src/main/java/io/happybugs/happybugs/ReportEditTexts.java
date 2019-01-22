package io.happybugs.happybugs;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    // Sets edit texts to invisibility.
    // Used when opening and collapsing answer texts on question click events.
    public void openAndCollapse(ReportEditTexts editTexts){
        editTexts.whatText.setVisibility(View.GONE);
        editTexts.whereText.setVisibility(View.GONE);
        editTexts.whenText.setVisibility(View.GONE);
        editTexts.whoText.setVisibility(View.GONE);
        editTexts.detailsText.setVisibility(View.GONE);
    }
}
