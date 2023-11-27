package com.example.interfaces_integradora;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Background_flur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_flur);
    }

    private void blurBackground()
    {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        bitmap = BlurBuilder.blur(this, bitmap, Color.GREEN);

        ImageView backgroundImageBlur = findViewById(R.id.backgroundImageBlur);
        backgroundImageBlur.setImageBitmap(bitmap);
        backgroundImageBlur.setVisibility(View.VISIBLE);

    }

}