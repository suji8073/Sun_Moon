package com.example.sun_moon;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class logout1 extends Dialog {

    private Context context;
    private CustomDialogClickListener1 customDialogClickListener;
    private TextView  tvNegative, tvPositive, option_codetype_dialog_title_tv;

    public logout1(@NonNull Context context, CustomDialogClickListener1 customDialogClickListener) {
        super(context);
        this.context = context;
        this.customDialogClickListener = customDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);

        tvPositive = findViewById(R.id.option_codetype_dialog_positive);
        tvNegative = findViewById(R.id.option_codetype_dialog_negative);
        option_codetype_dialog_title_tv = findViewById(R.id.option_codetype_dialog_title_tv);
        option_codetype_dialog_title_tv.setText("정말 앱을\n종료하시겠습니까?");

        tvPositive.setOnClickListener(v -> {
            // 로그아웃 취소
            this.customDialogClickListener.onPositiveClick();
            dismiss();
        });
        tvNegative.setOnClickListener(v -> {
            // 로그아웃 하자
            this.customDialogClickListener.onNegativeClick();
            dismiss();
        });
    }
}