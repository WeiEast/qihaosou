package com.qihaosou.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.qihaosou.util.L;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/1
 * Description:
 */
public class MyTextWacher implements TextWatcher {
    private EditText[] editTexts;
    private Button button;
    public MyTextWacher(Button button,EditText ... editTexts){

        this.editTexts=editTexts;
        this.button=button;

    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            Boolean isEnabled=true;
            for(EditText editText:editTexts){
                if(editText.getText().toString().length()==0){
                    isEnabled=false;
                    break;
                }
            }
        button.setEnabled(isEnabled);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
