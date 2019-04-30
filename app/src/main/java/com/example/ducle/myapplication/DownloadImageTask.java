package com.example.ducle.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class DownloadImageTask  extends AsyncTask<String, Void, ArrayList<Bitmap>> {

public interface AsyncResponse {
        void processFinish(ArrayList<Bitmap> output);
    }

    public AsyncResponse delegate = null;

    DownloadImageTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(String... urls) {
        ArrayList<Bitmap> array = new ArrayList<Bitmap>();

        for(int i = 0; i < urls.length; ++i) {
            final String url = urls[i];
            Bitmap bitmap = null;
            try {
                final InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                array.add(bitmap);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return array;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> array) {
        delegate.processFinish(array);
    }
}

