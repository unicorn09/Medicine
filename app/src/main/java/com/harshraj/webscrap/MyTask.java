package com.harshraj.webscrap;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;


public class MyTask extends AsyncTask<String, Void, String> {
    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse harshraj = null;

    public MyTask(AsyncResponse harshraj){
        this.harshraj = harshraj;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con =  (HttpURLConnection) new URL(params[0]).openConnection();
            con.setRequestMethod("HEAD");
            System.out.println(con.getResponseCode());
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK)
            return (params[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
        return "404";
    }

    @Override
    protected void onPostExecute(String result)
    {
        harshraj.processFinish(result);
    }
}
