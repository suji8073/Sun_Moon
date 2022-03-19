package com.example.sun_moon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

public class TextGradient extends androidx.appcompat.widget.AppCompatTextView {

    public TextGradient(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public TextGradient(Context context) {
        super(context);
    }

    public TextGradient(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        Shader shader = new LinearGradient(0, 0, 0, super.getTextSize(),
                Color.parseColor("#904805"), Color.parseColor("#80C03D03"),
                Shader.TileMode.CLAMP);
        super.getPaint().setShader(shader);
        super.setText(text.toString(), type);
    }
}