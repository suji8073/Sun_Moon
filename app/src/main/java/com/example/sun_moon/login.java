package com.example.sun_moon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    EditText name;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        name = findViewById(R.id.name);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    popup_show();
                } else {
                    editor.putString("name", String.valueOf(name.getText()));
                    editor.apply();

                    Intent start_intent = new Intent(login.this, list.class);
                    startActivity(start_intent);
                }
            }
        });
    }

    void popup_show(){
        popup_dialog octDialog = new popup_dialog(login.this, new CustomDialogClickListener() {
            @Override
            public void onPositiveClick() { //확인 버튼
                Log.i("Dialog", "확인");
            }
        });
        octDialog.setCanceledOnTouchOutside(true);
        octDialog.setCancelable(true);
        octDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        octDialog.show();
    }

}