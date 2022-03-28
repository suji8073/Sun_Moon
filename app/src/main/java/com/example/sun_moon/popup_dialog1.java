package com.example.sun_moon;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class popup_dialog1 extends Dialog {

    private Context context;
    private CustomDialogClickListener customDialogClickListener;
    private TextView tvPositive, option_codetype_dialog_title_tv;

    public popup_dialog1(@NonNull Context context, CustomDialogClickListener customDialogClickListener1) {
        super(context);
        this.context = context;
        this.customDialogClickListener = customDialogClickListener1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_dialog);

        option_codetype_dialog_title_tv = findViewById(R.id.option_codetype_dialog_title_tv);
        option_codetype_dialog_title_tv.setText("추후 추가 예정입니다.");

        tvPositive = findViewById(R.id.option_codetype_dialog_positive);
        tvPositive.setOnClickListener(v -> { // 확인 버튼
            // 저장버튼 클릭
            this.customDialogClickListener.onPositiveClick();
            dismiss();
        });
    }
}