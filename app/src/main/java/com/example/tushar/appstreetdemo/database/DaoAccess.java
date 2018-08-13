package com.example.tushar.appstreetdemo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void inertImages(List<Images> imageList);

    @Query("SELECT * FROM Images WHERE imageName LIKE :searchedString")
    List<Images> fetchImages(String searchedString);

}
