package com.example.sun_moon;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class logout extends Dialog {

    private Context context;
    private CustomDialogClickListener1 customDialogClickListener;
    private TextView tvTitle, tvNegative, tvPositive;

    public logout(@NonNull Context context, CustomDialogClickListener1 customDialogClickListener) {
        super(context);
        this.context = context;
        this.customDialogClickListener = customDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);

        tvTitle = findViewById(R.id.option_codetype_dialog_title_tv);
        tvPositive = findViewById(R.id.option_codetype_dialog_positive);
        tvNegative = findViewById(R.id.option_codetype_dialog_negative);

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