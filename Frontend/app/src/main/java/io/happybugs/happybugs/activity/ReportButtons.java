package io.happybugs.happybugs.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class ReportButtons extends AppCompatActivity {
    Button whatBtn;
    Button whereBtn;
    Button whenBtn;
    Button whoBtn;
    Button detailsBtn;
    Button saveBtn;

    public ReportButtons(Button whatBtn, Button whereBtn, Button whenBtn,
                   Button whoBtn, Button detailsBtn, Button saveBtn) {
        this.whatBtn = whatBtn;
        this.whereBtn = whereBtn;
        this.whenBtn = whenBtn;
        this.whoBtn = whoBtn;
        this.detailsBtn = detailsBtn;
        this.saveBtn = saveBtn;
    }

    public Button getWhatBtn() {
        return whatBtn;
    }

    public Button getWhereBtn() {
        return whereBtn;
    }

    public Button getWhenBtn() {
        return whenBtn;
    }

    public Button getWhoBtn() {
        return whoBtn;
    }

    public Button getDetailsBtn() {
        return detailsBtn;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }
}
