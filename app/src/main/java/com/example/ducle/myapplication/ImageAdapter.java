package com.example.ducle.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private ArrayList<Image> array;

    ImageAdapter( ArrayList<Image> array) {
        this.array = array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image img = array.get(position);
        if (convertView == null) {
            return img.getV();
        }
        convertView = img.getV();
        return convertView;
    }

    @Override
    public int getCount() {
        return array.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
}
