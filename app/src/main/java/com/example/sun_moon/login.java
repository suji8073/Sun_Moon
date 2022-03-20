package com.example.sun_moon;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        EditText name = findViewById(R.id.name);

        Button next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                    builder.setMessage("이름을 입력해주세요.");
                    builder.setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=builder.create();
                    alert.show();
                }

                else{
                    editor.putString("name", String.valueOf(name.getText()));
                    editor.apply();

                    Intent start_intent = new Intent(login.this, list.class);
                    //start_intent.putExtra("이름", name.getText());
                    startActivity(start_intent);
                }
            }
        });

}}