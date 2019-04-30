package com.example.ducle.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class FullScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        byte [] byteArray = getIntent().getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView imageView = findViewById(R.id.image_view);
        imageView.setImageBitmap(bitmap);

    }

    public void closeScreen(View view) {
        finish();
    }
}
