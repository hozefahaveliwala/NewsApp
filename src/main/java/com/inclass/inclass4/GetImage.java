
package com.inclass.inclass4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.ArrayList;


public class GetImage extends AsyncTask <String, Void, Bitmap>{

    setNewsImage sNI;

    public GetImage(setNewsImage sNI) {
        this.sNI = sNI;
    }

    @Override
    protected void onPreExecute() {
            super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
            return image;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        sNI.setImage(result);
    }
    static public  interface setNewsImage {
       public void setImage(Bitmap bitmap);
    }

}