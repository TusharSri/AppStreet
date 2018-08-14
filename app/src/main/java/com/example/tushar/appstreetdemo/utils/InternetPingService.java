package com.example.tushar.appstreetdemo.utils;


import android.content.Context;
import android.os.AsyncTask;

import com.example.tushar.appstreetdemo.interfaces.PingServiceCallback;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class InternetPingService extends AsyncTask<Void, Void, Void> {

    private boolean isConnected;
    private PingServiceCallback pingServiceCallback;
    private Context context;

    public InternetPingService(Context context) {
        this.context = context;
        pingServiceCallback = (PingServiceCallback) context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            isConnected = isInternetReachable();
        } catch (InterruptedException e) {
            isConnected = true;
            e.printStackTrace();
        } catch (IOException e) {
            isConnected = true;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pingServiceCallback.PingServiceCallback(isConnected);
    }

    /**
     * Method to ping the google.com url to know whether internet is available or not after we are
     * connected to wifi or other network
     */
    public void isInternetAccessible() {
        try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("https://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(5000);
            urlc.connect();
            if (urlc.getResponseCode() == 200) {
                // host reachable
                isConnected = true;
            } else {
                // host not reachable
                isConnected = false;
            }
        } catch (IOException e) {
            isConnected = false;
           //Logger.e("InternetPingService", "Couldn't check internet connection : " + e.getMessage());
        } catch (Exception e) {
            isConnected = true;
            //Logger.e("InternetPingService", "Couldn't check internet connection : " + e.getMessage());
        }
    }

    public boolean isInternetReachable() throws InterruptedException, IOException {
        // String command = "ping -i 0.2 -c 1 -w 1000 google.com";

        return InetAddress.getByName("https://www.google.com").isReachable(1000);
    }
}
