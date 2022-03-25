package com.example.sun_moon;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class popup_dialog extends Dialog {

    private Context context;
    private CustomDialogClickListener customDialogClickListener;
    private TextView tvPositive;

    public popup_dialog(@NonNull Context context, CustomDialogClickListener customDialogClickListener1) {
        super(context);
        this.context = context;
        this.customDialogClickListener = customDialogClickListener1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_dialog);

        tvPositive = findViewById(R.id.option_codetype_dialog_positive);
        tvPositive.setOnClickListener(v -> { // 확인 버튼
            // 저장버튼 클릭
            this.customDialogClickListener.onPositiveClick();
            dismiss();
        });
    }
}