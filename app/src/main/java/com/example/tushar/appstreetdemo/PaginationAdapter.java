package com.example.tushar.appstreetdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tushar.appstreetdemo.database.Images;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Images> imageUrls;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        imageUrls = new ArrayList<>();
    }

    public List<Images> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<Images> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Images imageUrl = imageUrls.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                MovieVH movieVH = (MovieVH) holder;

                new DownloadImageTask(movieVH.imageView).execute(imageUrl.getImageName());

                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return imageUrls == null ? 0 : imageUrls.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == imageUrls.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Images mc) {
        imageUrls.add(mc);
        notifyItemInserted(imageUrls.size() - 1);
    }

    public void addAll(List<Images> mcList) {
        for (Images mc : mcList) {
            add(mc);
        }
    }

    public void remove(Images city) {
        int position = imageUrls.indexOf(city);
        if (position > -1) {
            imageUrls.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Images());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = imageUrls.size() - 1;
        Images item = getItem(position);

        if (item != null) {
            imageUrls.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Images getItem(int position) {
        return imageUrls.get(position);
    }


   /*
   View Holders
   ___________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MovieVH(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_text);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
            this.bmImage.setImageResource(R.drawable.ic_launcher_background);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
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