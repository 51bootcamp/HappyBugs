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

public class WhoTextChange implements TextWatcher {
    View view;
    EditText facebookIDText;
    EditText whoText;
    CheckBox whoCheckBox;
    Button saveBtn;


    public WhoTextChange(View view, EditText facebookIDText, EditText whoText, CheckBox whoCheckBox, Button saveBtn) {
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

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (whoText.getText().hashCode() == s.hashCode()){
            whoCheckBox.setChecked((whoText.getText().length() > 0)? true: false);
        } else if (facebookIDText.getText().hashCode() == s.hashCode()) {
            whoCheckBox.setChecked((facebookIDText.getText().length() > 25) ? true : false);

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
