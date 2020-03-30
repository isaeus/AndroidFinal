package com.example.androidfinal;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FetchWorker extends AsyncTask<String, String, String> {
    private fetchWorkerListener fwListener;
    public FetchWorker(fetchWorkerListener fwListener){
        this.fwListener = fwListener;
    }

    @Override
    protected String doInBackground(String... params) {

        try{

            URL url = new URL(params[0]);
            Log.d("url", params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200){
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder sb = new StringBuilder();
                String line;

                try{
                    while((line = bufferedReader.readLine()) != null){
                        sb.append(line);
                    }
                }
                catch (IOException e){
                    Log.d("Data Error", e.getMessage());
                }
                //passed on to onPostExecute
                return sb.toString();
            }
            else{
                Log.d("StatusCode Error", statusCode + "");
            }
        }
        catch (Exception e){
            Log.d("test", e.getLocalizedMessage());
        }
        //returned if the try catch block fails
        return "";
    }

    @Override
    protected void onPostExecute(String result){
        Log.d("Data", result);
        fwListener.getResult(result);
    }

    interface fetchWorkerListener{
        List getResult(String jsonData);
    }

}
