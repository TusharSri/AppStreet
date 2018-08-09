package com.example.tushar.appstreetdemo;

import java.util.ArrayList;
import java.util.List;

public class ImageUrl {

    private String url;

    public ImageUrl() {
    }

    public ImageUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * Creating 10 dummy content for list.
     *
     * @param itemCount
     * @return
     */
    public static List<ImageUrl> createMovies(int itemCount) {
        List<ImageUrl> imageUrls = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ImageUrl imageUrl = new ImageUrl("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }
}
