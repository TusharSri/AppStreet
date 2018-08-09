package com.example.tushar.appstreetdemo;

import android.arch.persistence.room.Room;
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

import android.widget.ProgressBar;

import com.example.tushar.appstreetdemo.database.ImageDatabase;
import com.example.tushar.appstreetdemo.database.Images;
import com.example.tushar.appstreetdemo.utils.PaginationScrollListener;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private GridLayoutManager gridLayoutManager;
    private PaginationAdapter adapter;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;
    private static final String DATABASE_NAME = "image_db";
    private ImageDatabase movieDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initViews();
        addDb();
        loadFirstPage();
    }

    private void initViews() {
        // Attaching the layout to the toolbar object
        Toolbar toolbar =  findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);
        gridLayoutManager = new GridLayoutManager(this,1);
        rv =  findViewById(R.id.main_recycler);
        rv.setLayoutManager(gridLayoutManager);
        progressBar =  findViewById(R.id.main_progress);
        adapter = new PaginationAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                TOTAL_PAGES +=1;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void addDb() {
        movieDatabase = Room.databaseBuilder(getApplicationContext(),
                ImageDatabase.class, DATABASE_NAME)
                .build();
    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        ImageUrl.apiCall(movieDatabase);
        progressBar.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Images> imageUrls = movieDatabase.daoAccess().fetchImages();
                adapter.addAll(imageUrls);
                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }
        }).start();
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        ImageUrl.apiCall(movieDatabase);

        adapter.removeLoadingFooter();
        isLoading = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Images> imageUrls = movieDatabase.daoAccess().fetchImages();
                adapter.addAll(imageUrls);
                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }
        }).start();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_one:
                gridLayoutManager = new GridLayoutManager(this,1);
                rv.setLayoutManager(gridLayoutManager);
                return true;
            case R.id.action_two:
                gridLayoutManager = new GridLayoutManager(this,2);
                rv.setLayoutManager(gridLayoutManager);
                return true;
            case R.id.action_three:
                gridLayoutManager = new GridLayoutManager(this,3);
                rv.setLayoutManager(gridLayoutManager);
                return true;
            case R.id.action_four:
                gridLayoutManager = new GridLayoutManager(this,4);
                rv.setLayoutManager(gridLayoutManager);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}