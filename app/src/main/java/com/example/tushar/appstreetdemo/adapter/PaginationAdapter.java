package com.example.tushar.appstreetdemo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tushar.appstreetdemo.R;
import com.example.tushar.appstreetdemo.database.Images;
import com.example.tushar.appstreetdemo.interfaces.ImageClickListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private List<Images> imageUrls;
    private ImageClickListener clicklistener = null;
    private boolean isLoadingAdded = false;
    private static final int LOADING = 1;

    public PaginationAdapter() {
        imageUrls = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new ImageViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Images imageUrl = imageUrls.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                new DownloadImageTask(imageViewHolder.imageView).execute(imageUrl.getImageName());
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

    protected class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.item_text);
        }

        @Override
        public void onClick(View v) {
            if (clicklistener != null) {
                clicklistener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ImageClickListener clicklistener) {
        this.clicklistener = clicklistener;
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
                String photoPath = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + "AppStreet" + "/" +urldisplay;
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