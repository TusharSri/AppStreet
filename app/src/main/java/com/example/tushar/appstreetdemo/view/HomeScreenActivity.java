package com.example.tushar.appstreetdemo.view;

import android.app.DownloadManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tushar.appstreetdemo.adapter.PaginationAdapter;
import com.example.tushar.appstreetdemo.R;
import com.example.tushar.appstreetdemo.database.ImageDatabase;
import com.example.tushar.appstreetdemo.database.Images;
import com.example.tushar.appstreetdemo.interfaces.JobCallBack;
import com.example.tushar.appstreetdemo.model.ImageResponse;
import com.example.tushar.appstreetdemo.networking.ImageUrlJob;
import com.example.tushar.appstreetdemo.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity implements JobCallBack {

    private static final String TAG = HomeScreenActivity.class.getSimpleName();
    private GridLayoutManager gridLayoutManager;
    private PaginationAdapter paginationAdapter;
    private RecyclerView homeRecyclerView;
    private static final String DATABASE_NAME = "image_db";
    private ImageDatabase movieDatabase;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initViews();
        addDb();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridLayoutManager = new GridLayoutManager(this, 1);
        homeRecyclerView = findViewById(R.id.home_recyclerview);
        homeRecyclerView.setLayoutManager(gridLayoutManager);
        paginationAdapter = new PaginationAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        homeRecyclerView.setLayoutManager(linearLayoutManager);
        homeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        homeRecyclerView.setAdapter(paginationAdapter);
        searchEditText = findViewById(R.id.search_editText);
    }

    private void addDb() {
        movieDatabase = Room.databaseBuilder(getApplicationContext(),
                ImageDatabase.class, DATABASE_NAME)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:
                gridLayoutManager = new GridLayoutManager(this, 1);
                homeRecyclerView.setLayoutManager(gridLayoutManager);
                return true;
            case R.id.action_two:
                gridLayoutManager = new GridLayoutManager(this, 2);
                homeRecyclerView.setLayoutManager(gridLayoutManager);
                return true;
            case R.id.action_three:
                gridLayoutManager = new GridLayoutManager(this, 3);
                homeRecyclerView.setLayoutManager(gridLayoutManager);
                return true;
            case R.id.action_four:
                gridLayoutManager = new GridLayoutManager(this, 4);
                homeRecyclerView.setLayoutManager(gridLayoutManager);
                return true;
            case R.id.action_search:
                String searchedString = searchEditText.getText().toString().trim();
                if (searchedString.length() > 0) {
                    Utils.showProgressDialog(this, "Fetching data, Please wait");
                    callForRepo(searchedString);
                } else {
                    Toast.makeText(this, "Please enter somthing to search", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Checking the repository to show data
     *
     * @param searchedString this is the input entered by user
     */
    private void callForRepo(String searchedString) {
        new FetchDataFromDB().execute(searchedString);
    }

    /**
     * Set Data to Recyclerview adapter
     *
     * @param imageUrls the url to search
     */
    private void setDataToAdapter(List<Images> imageUrls) {
        paginationAdapter.addAll(imageUrls);
    }

    /**
     * Async class to fetch data from database
     */
    public class FetchDataFromDB extends AsyncTask<String, Integer, String> {
        List<Images> imageUrls = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {
            String searchedString = params[0];
            imageUrls = movieDatabase.daoAccess().fetchImages(searchedString);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (imageUrls.size() <= 0) {
                callApiToGetImages();
            } else {
                setDataToAdapter(imageUrls);
                paginationAdapter.notifyDataSetChanged();
                Utils.hideProgressDialog();
            }
        }
    }

    /**
     * Call the api to get the data
     */
    private void callApiToGetImages() {
        ImageUrlJob.getImageUrl(movieDatabase, this);
    }

    @Override
    public void onSuccess(Object object) {
        Utils.hideProgressDialog();

        ImageResponse imageResponse = (ImageResponse) object;
        List<Images> img = new ArrayList<>();
        for (int i = 0; i < imageResponse.getArticles().size(); i++) {
            Images images = new Images();
            images.setImageUrl(imageResponse.getArticles().get(i).getUrlToImage());
            img.add(images);
        }

        new SaveDataToDB(img).execute();
        setDataToAdapter(img);
        paginationAdapter.notifyDataSetChanged();
        Utils.hideProgressDialog();
    }

    @Override
    public void onFailure() {
        Utils.hideProgressDialog();
    }

    public class SaveDataToDB extends AsyncTask<String, Integer, String> {

        private List<Images> imagesList;

        private SaveDataToDB(List<Images> imagesList) {
            this.imagesList = imagesList;
        }

        @Override
        protected String doInBackground(String... strings) {

            for (int i = 0; i < imagesList.size(); i++) {
                String filename = imagesList.get(i).getImageUrl();
                String downloadUrlOfImage = imagesList.get(i).getImageUrl();
                File direct =
                        new File(Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                .getAbsolutePath() + "/" + "AppStreet" + "/");

                if (!direct.exists()) {
                    direct.mkdir();
                    Log.d(TAG, "dir created for first time");
                }

                DownloadManager dm = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(downloadUrlOfImage);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(filename)
                        .setMimeType("image/jpeg")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                                File.separator + "AppStreet" + File.separator + filename);

                dm.enqueue(request);
            }
            movieDatabase.daoAccess().inertImages(imagesList);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setDataToAdapter(imagesList);
            paginationAdapter.notifyDataSetChanged();
            Utils.hideProgressDialog();
        }
    }
}