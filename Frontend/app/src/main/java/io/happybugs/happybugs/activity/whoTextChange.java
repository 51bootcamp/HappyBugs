package io.happybugs.happybugs.activity;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import io.happybugs.happybugs.R;

public class whoTextChange implements TextWatcher {
    View view;
    EditText facebookIDText;
    EditText whoText;
    CheckBox whoCheckBox;
    Button saveBtn;


    public whoTextChange(View view, EditText facebookIDText, EditText whoText, CheckBox whoCheckBox, Button saveBtn) {
        this.view = view;
        this.facebookIDText = facebookIDText;
        this.whoText = whoText;
        this.whoCheckBox = whoCheckBox;
        this.saveBtn = saveBtn;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        switch (view.getId()){
            case R.id.whoText:
                whoCheckBox.setChecked((whoText.getText().length() > 0)? true: false);
            case R.id.facebook_edit_text:
                if (!s.toString().startsWith("https://www.facebook.com/")) {
                    facebookIDText.setText("https://www.facebook.com/");
                    Selection.setSelection((Spannable) facebookIDText.getText(),
                            facebookIDText.getText().length());
                }
                whoCheckBox.setChecked((facebookIDText.getText().length() > 25)? true: false);
        }
        saveBtn.setEnabled(true);
    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (view.getId()){
            case R.id.facebook_edit_text:
                // Set prefix for facebook ID profile link.
                if (!s.toString().startsWith("https://www.facebook.com/")) {
                    facebookIDText.setText("https://www.facebook.com/");
                    Selection.setSelection((Spannable) facebookIDText.getText(),
                            facebookIDText.getText().length());
                }
        }
        whoCheckBox.setChecked((whoText.getText().length() != 0) ||
                (facebookIDText.getText().length() > 25));
        saveBtn.setEnabled(true);
    }
}
