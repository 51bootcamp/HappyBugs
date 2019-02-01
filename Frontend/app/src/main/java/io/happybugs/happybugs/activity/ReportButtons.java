package io.happybugs.happybugs.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;

public class ReportButtons extends AppCompatActivity {
    Button whatBtn;
    Button whereBtn;
    Button whenBtn;
    Button whoBtn;
    Button detailsBtn;
    Button saveBtn;
    ImageButton closeBtn;

    public ReportButtons(Button whatBtn, Button whereBtn, Button whenBtn,
                   Button whoBtn, Button detailsBtn, Button saveBtn, ImageButton closeBtn) {
        this.whatBtn = whatBtn;
        this.whereBtn = whereBtn;
        this.whenBtn = whenBtn;
        this.whoBtn = whoBtn;
        this.detailsBtn = detailsBtn;
        this.saveBtn = saveBtn;
        this.closeBtn = closeBtn;
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
