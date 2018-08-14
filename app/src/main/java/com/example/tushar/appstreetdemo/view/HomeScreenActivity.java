package com.example.tushar.appstreetdemo.view;

import android.app.DownloadManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tushar.appstreetdemo.adapter.PaginationAdapter;
import com.example.tushar.appstreetdemo.R;
import com.example.tushar.appstreetdemo.database.ImageDatabase;
import com.example.tushar.appstreetdemo.database.Images;
import com.example.tushar.appstreetdemo.interfaces.ImageClickListener;
import com.example.tushar.appstreetdemo.interfaces.JobCallBack;
import com.example.tushar.appstreetdemo.interfaces.PingServiceCallback;
import com.example.tushar.appstreetdemo.model.ImageResponse;
import com.example.tushar.appstreetdemo.networking.ImageUrlJob;
import com.example.tushar.appstreetdemo.utils.InternetPingService;
import com.example.tushar.appstreetdemo.utils.NetworkStateReceiver;
import com.example.tushar.appstreetdemo.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity implements JobCallBack, ImageClickListener, PingServiceCallback {

    private static final String TAG = HomeScreenActivity.class.getSimpleName();
    private GridLayoutManager gridLayoutManager;
    private PaginationAdapter paginationAdapter;
    private RecyclerView homeRecyclerView;
    private static final String DATABASE_NAME = "image_db";
    private ImageDatabase imageDatabase;
    private EditText searchEditText;
    private String searchedString;
    private RelativeLayout offlineBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initViews();
        addDb();
    }

    private void initViews() {
        registerReceiver(broadcastReceiver, new IntentFilter("INTERNET_LOST"));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridLayoutManager = new GridLayoutManager(this, 1);
        offlineBanner = findViewById(R.id.offline_banner_relative);
        homeRecyclerView = findViewById(R.id.home_recyclerview);
        homeRecyclerView.setLayoutManager(gridLayoutManager);
        paginationAdapter = new PaginationAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        homeRecyclerView.setLayoutManager(linearLayoutManager);
        homeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        homeRecyclerView.setAdapter(paginationAdapter);
        paginationAdapter.setClickListener(this);
        searchEditText = findViewById(R.id.search_editText);
    }

    private void addDb() {
        imageDatabase = Room.databaseBuilder(getApplicationContext(),
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
                searchedString = searchEditText.getText().toString().trim();
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
     * Open Full sreen activity when an item image is clicked
     *
     * @param view
     * @param position
     */
    @Override
    public void itemClicked(View view, int position) {
        Intent intent = new Intent(this, FullImageActivity.class);
        intent.putExtra("searchedString", searchedString);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    /**
     * Async class to fetch data from database
     */
    public class FetchDataFromDB extends AsyncTask<String, Integer, String> {
        List<Images> imageUrls = new ArrayList<>();
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
            if (imageUrls.size() <= 0) {
                callApiToGetImages(searchedString);
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
    private void callApiToGetImages(String searchedString) {
        ImageUrlJob.getImageUrl(searchedString, imageDatabase, this);
    }

    @Override
    public void onSuccess(Object object) {
        Utils.hideProgressDialog();

        ImageResponse imageResponse = (ImageResponse) object;
        List<Images> img = new ArrayList<>();
        String filename;
        for (int i = 0; i < imageResponse.getValue().size(); i++) {
            filename = searchedString + i + ".jpg";
            Images images = new Images();
            images.setImageUrl(imageResponse.getValue().get(i).getContentUrl());
            images.setImageName(filename);
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
        Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
    }

    public class SaveDataToDB extends AsyncTask<String, Integer, String> {

        private List<Images> imagesList;

        private SaveDataToDB(List<Images> imagesList) {
            this.imagesList = imagesList;
        }

        @Override
        protected String doInBackground(String... strings) {
            List<Images> imageslst = new ArrayList<>();
            for (int i = 0; i < imagesList.size(); i++) {
                String filename = searchedString + i + ".jpg";
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
                        .setTitle(searchedString + i)
                        .setMimeType("image/jpeg")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                                File.separator + "AppStreet" + File.separator + filename);

                dm.enqueue(request);
                Images images = new Images();
                images.setImageName(filename);
                images.setImageUrl(filename);
                imageslst.add(images);
            }
            imageDatabase.daoAccess().inertImages(imageslst);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.isNetworkAvailable(this)) {
            new InternetPingService(this).execute();
        } else {
            offlineBanner.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void PingServiceCallback(boolean isConnected) {
        showBanner(isConnected);
    }

    public void showBanner(boolean b) {
        if (b) {
            offlineBanner.setVisibility(View.GONE);
        } else {
            offlineBanner.setVisibility(View.VISIBLE);
        }
    }

    NetworkStateReceiver broadcastReceiver = new NetworkStateReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showBanner(intent.getBooleanExtra("isConnected", false));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

