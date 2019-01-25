package io.happybugs.happybugs.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;

public class ReportCheckBoxes extends AppCompatActivity {
    CheckBox whatCheck;
    CheckBox whereCheck;
    CheckBox whenCheck;
    CheckBox whoCheck;
    CheckBox detailsCheck;

    public ReportCheckBoxes(CheckBox whatCheck, CheckBox whereCheck,
                      CheckBox whenCheck, CheckBox whoCheck, CheckBox detailsCheck) {
        this.whatCheck = whatCheck;
        this.whereCheck = whereCheck;
        this.whenCheck = whenCheck;
        this.whoCheck = whoCheck;
        this.detailsCheck = detailsCheck;
    }

    public CheckBox getWhatCheck() {
        return whatCheck;
    }

    public CheckBox getWhereCheck() {
        return whereCheck;
    }

    public CheckBox getWhenCheck() {
        return whenCheck;
    }

    public CheckBox getWhoCheck() {
        return whoCheck;
    }

    public CheckBox getDetailsCheck() {
        return detailsCheck;
    }
}
