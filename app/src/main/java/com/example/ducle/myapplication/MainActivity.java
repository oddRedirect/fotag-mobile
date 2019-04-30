package com.example.ducle.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Configuration;
import android.view.View;
import android.widget.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Bitmap> bitmapArray = new ArrayList<>();
    private ArrayList<Image> imgs = new ArrayList<>();
    private ArrayList<Image> tempImgs = new ArrayList<>();
    private float rating = 0;
    private boolean filterOn = false;

    public void enlargeImage(View view) {
        Intent intent = new Intent(this, FullScreen.class);
        ImageView imageView = (ImageView) view;

        //get bitmap image
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteArray = stream.toByteArray();


        intent.putExtra("bitmap", byteArray);
        startActivity(intent);
    }

    private void doFilter() {

        //add from tempImgs any that are above rating
        for (int i = 0; i < tempImgs.size(); ++i) {
            float curRating = tempImgs.get(i).getRating();
            if (curRating >= rating) {
                imgs.add(tempImgs.get(i));
                tempImgs.remove(i);
                i--;
            }
        }

        //filter everything below rating
        for (int i = 0; i < imgs.size(); ++i) {
            float curRating = imgs.get(i).getRating();

            if (curRating < rating) {
                tempImgs.add(imgs.get(i));
                imgs.remove(i);
                i--;
            }
        }

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(imgs));
        gridView.invalidateViews();
    }

    public void filter(View view) {
        int id = view.getId();
        filterOn = true;
        int[] arrayID = {R.id.star1, R.id.star2, R.id.star3, R.id.star4, R.id.star5};

        switch (id) {
            case R.id.star1:
                rating = 1;
                break;
            case R.id.star2:
                rating = 2;
                break;
            case R.id.star3:
                rating = 3;
                break;
            case R.id.star4:
                rating = 4;
                break;
            case R.id.star5:
                rating = 5;
                break;
        }

        for (int i = R.id.star1; i <= id; ++i) {
            ((ImageButton) findViewById(i)).setImageResource(R.drawable.ic_star_black_24dp);
        }

        for (int i = id + 1; i <= R.id.star5; ++i) {
            ((ImageButton) findViewById(i)).setImageResource(R.drawable.ic_star);
        }

        doFilter();
    }

    private ArrayList<Image> createImgArray() {
        ArrayList<Image> imgs = new ArrayList<>();
        for (int i = 0; i < bitmapArray.size(); ++i) {
            Image img = new Image(this, bitmapArray.get(i));

            img.getRatingBar().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float curRating, boolean fromUser) {
                    if (filterOn && curRating < rating) { //only filter if its less
                        doFilter();
                    }
                    GridView gridView = (GridView) findViewById(R.id.gridview);
                    gridView.invalidateViews();
                }
            });
            imgs.add(img);
        }
        return imgs;
    }

    private void resetStars() {
        for (int i = R.id.star1; i <= R.id.star5; ++i) {
            ((ImageButton) findViewById(i)).setImageResource(R.drawable.ic_star);
        }
    }

    public void loadImages(View view) {
        resetStars();
        filterOn = false;
        GridView gridView = (GridView) findViewById(R.id.gridview);
        imgs = createImgArray();

        gridView.setAdapter(new ImageAdapter(imgs));
        gridView.invalidateViews();
    }

    public void clearImages(View view) {
        resetStars();
        filterOn = false;
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(null);
    }


    private void downloadImages() {

        DownloadImageTask asyncTask = new DownloadImageTask(new DownloadImageTask.AsyncResponse() {

            @Override
            public void processFinish(ArrayList<Bitmap> output) {
                bitmapArray = output;
            }
        });

        asyncTask.execute("https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/bunny.jpg",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/chinchilla.jpg",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/doggo.jpg",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/fox.jpg",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/hamster.jpg",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/husky.jpg",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/kitten.png",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/loris.jpg",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/puppy.jpg",
                "https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/images/sleepy.png");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView = (TextView) findViewById(R.id.textview1);
            textView.setText("Fotag Mobile (d9le) ");
        }
        downloadImages();
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                GridView gridView = (GridView) findViewById(R.id.gridview);
                if (gridView.getChildAt(0).getTop() == 0) {
                    gridView.setAdapter(new ImageAdapter(imgs));
                    gridView.invalidateViews();
                }

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    gridView.invalidateViews();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        TextView textView = (TextView) findViewById(R.id.textview1);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            textView.setText("Fotag Mobile (d9le) ");
            GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.setAdapter(new ImageAdapter(imgs));
            gridView.invalidateViews();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            textView.setText("");
            GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.setAdapter(new ImageAdapter(imgs));
            gridView.invalidateViews();
        }
    }


}
