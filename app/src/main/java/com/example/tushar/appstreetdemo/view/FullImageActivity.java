package com.example.tushar.appstreetdemo.view;

import android.arch.persistence.room.Room;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tushar.appstreetdemo.R;
import com.example.tushar.appstreetdemo.adapter.PaginationAdapter;
import com.example.tushar.appstreetdemo.database.ImageDatabase;
import com.example.tushar.appstreetdemo.database.Images;
import com.example.tushar.appstreetdemo.utils.OnSwipeTouchListener;
import com.example.tushar.appstreetdemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FullImageActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "image_db";
    private ImageDatabase imageDatabase;
    private ImageView imageView;
    private int position;
    private List<Images> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        addDb();
        Intent intent = getIntent();
        imageUrls = new ArrayList<>();
        position = intent.getIntExtra("position", 0);
        imageView = findViewById(R.id.full_image_view);
        imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                if (position < imageUrls.size()-1) {
                    position = position + 1;
                    setImageView(position);
                } else {
                    Toast.makeText(FullImageActivity.this, "First Image Reached", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipeRight() {
                if (position > 0) {
                    position = position - 1;
                    setImageView(position);
                } else {
                    Toast.makeText(FullImageActivity.this, "Last Image Reached", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Utils.showProgressDialog(this, "Please wait, Loading image");
        new FullImageActivity.FetchDataFromDB().execute(intent.getStringExtra("searchedString"));
    }

    private void addDb() {
        imageDatabase = Room.databaseBuilder(getApplicationContext(),
                ImageDatabase.class, DATABASE_NAME)
                .build();
    }

    /**
     * Async class to fetch data from database
     */
    public class FetchDataFromDB extends AsyncTask<String, Integer, String> {
        String searchedString = "";

        @Override
        protected String doInBackground(String... params) {
            searchedString = params[0];
            imageUrls = imageDatabase.daoAccess().fetchImages("%" + searchedString + "%");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setImageView(position);
            Utils.hideProgressDialog();
        }
    }

    private void setImageView(int pos) {
        new SetImageTask(imageView).execute(imageUrls.get(pos).getImageName());
    }

    private class SetImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public SetImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
            this.bmImage.setImageResource(R.drawable.ic_launcher_background);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                String photoPath = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + "AppStreet" + "/" + urldisplay;
                mIcon11 = BitmapFactory.decodeFile(photoPath);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
