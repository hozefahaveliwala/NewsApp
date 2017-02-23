
package com.inclass.inclass4;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class GetNews extends AsyncTask<String, Void, String> {
    changeButtons cB;

    public GetNews(changeButtons cB) {
        this.cB = cB;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        String newsString = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sB = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sB.append(line);
                Log.d("SB", line);
            }
            newsString = sB.toString();
            return newsString;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        ArrayList<News> newsArrayList = new ArrayList<News>();
        super.onPostExecute(result);

//JSON
        if (result != null) {
            try {
                JSONObject jObject = new JSONObject(result);
                JSONArray jArray = jObject.getJSONArray("articles");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jarticles = jArray.getJSONObject(i);
                    News news = new News();
                    news.setAuthor(jarticles.getString("author"));
                    news.setDescription(jarticles.getString("description"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-DD");
                    Date d=sdf.parse(jarticles.getString("publishedAt"));

                    news.setPublishAt(d);
                    news.setTitle(jarticles.getString("title"));
                    news.setUrlToImage(jarticles.getString("urlToImage"));

                    newsArrayList.add(news);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();

            }
            cB.setNewsArrayList(newsArrayList);
            cB.enableNavigation();

        }
    }


    static public  interface changeButtons {
        public void enableNavigation();
        public void setNewsArrayList(ArrayList<News> aL);
    }
}