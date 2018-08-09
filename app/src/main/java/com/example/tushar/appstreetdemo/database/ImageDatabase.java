package com.example.tushar.appstreetdemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Images.class}, version = 1, exportSchema = false)
public abstract class ImageDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess() ;
}