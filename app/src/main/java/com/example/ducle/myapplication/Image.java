package com.example.ducle.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

public class Image {
    private View v;
    private ImageView imageView;
    private RatingBar ratingBar;

    Image(Context context, Bitmap bmp) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View thumbnail = inflater.inflate(R.layout.thumbnail, null);
        ImageView imageView = thumbnail.findViewById(R.id.imageview);
        imageView.setImageBitmap(bmp);
        RatingBar ratingBar = thumbnail.findViewById(R.id.ratingview);

        v = thumbnail;
        this.imageView = imageView;
        this.ratingBar = ratingBar;
    }

    View getV() {
        return v;
    }

    public ImageView getImageView() {
        return imageView;
    }

    RatingBar getRatingBar() {
        return ratingBar;
    }

    float getRating() {
        return ratingBar.getRating();
    }
}
