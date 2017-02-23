

package com.inclass.inclass4;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetNews.changeButtons, GetImage.setNewsImage {
    Spinner newsSpinner;
    String finalURL;
    ArrayList<News> newsArrayList;
    Button btnFinish, btnGetNews;
    ImageView iVNext, iVPrevious, iVLast, iVFirst, iVNewsImage;
    TextView textViewNews;
    int newsCount = 0, newsArrayMaxindex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsSpinner = (Spinner) findViewById(R.id.spinnerSource);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnGetNews = (Button) findViewById(R.id.buttonGetNews);
        iVFirst = (ImageView) findViewById(R.id.imageViewFirst);
        iVLast = (ImageView) findViewById(R.id.imageViewLast);
        iVNext = (ImageView) findViewById(R.id.imageViewNext);
        iVPrevious = (ImageView) findViewById(R.id.imageViewPrevious);
        iVNewsImage = (ImageView) findViewById(R.id.imageViewNews);
        textViewNews = (TextView) findViewById(R.id.textViewNews);

        iVFirst.setEnabled(false);
        iVLast.setEnabled(false);
        iVNext.setEnabled(false);
        iVPrevious.setEnabled(false);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,
                getResources().getStringArray(R.array.NewArray)) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        newsSpinner.setAdapter(arrayAdapter);


        btnGetNews.setOnClickListener(new View.OnClickListener() {
            String newsSource = null;

            @Override
            public void onClick(View v) {
                if (isConnected() == true) {
                    if (newsSpinner.getSelectedItemPosition() == 1) {
                        newsSource = "bbc-news";
                    } else if (newsSpinner.getSelectedItemPosition() == 2) {
                        newsSource = "cnn";
                    } else if (newsSpinner.getSelectedItemPosition() == 3) {
                        newsSource = "buzzfeed";
                    } else if (newsSpinner.getSelectedItemPosition() == 4) {
                        newsSource = "espn";
                    } else if (newsSpinner.getSelectedItemPosition() == 5) {
                        newsSource = "sky-news";
                    }


                    finalURL = getResources().getString(R.string.URL1) + getResources().getString(R.string.APIKey) + "&" + getResources().getString(R.string.URL2) + newsSource;
                    Log.d("URL", finalURL);
                    newsArrayList = new ArrayList<News>();
                    newsCount = 0;
                    new GetNews(MainActivity.this).execute(finalURL);

                } else {
                    Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iVFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsCount = 0;
                textViewNews.setText(newsArrayList.get(newsCount).toString());
                new GetImage(MainActivity.this).execute(newsArrayList.get(newsCount).getUrlToImage());
            }
        });
        iVPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsCount == 0) {
                    newsCount = newsArrayMaxindex;
                } else {
                    newsCount--;
                }
                textViewNews.setText(newsArrayList.get(newsCount).toString());
                new GetImage(MainActivity.this).execute(newsArrayList.get(newsCount).getUrlToImage());
            }

        });
        iVNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsCount == newsArrayMaxindex) {
                    newsCount = 0;
                } else {
                    newsCount++;
                }
                textViewNews.setText(newsArrayList.get(newsCount).toString());
                new GetImage(MainActivity.this).execute(newsArrayList.get(newsCount).getUrlToImage());
            }
        });
        iVLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsCount = newsArrayMaxindex;
                textViewNews.setText(newsArrayList.get(newsCount).toString());
                new GetImage(MainActivity.this).execute(newsArrayList.get(newsCount).getUrlToImage());
            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager cM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cM.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            return true;
        }
        return false;
    }

    @Override
    public void enableNavigation() {
        btnFinish.setEnabled(true);
        iVFirst.setEnabled(true);
        iVLast.setEnabled(true);
        iVNext.setEnabled(true);
        iVPrevious.setEnabled(true);

    }

    @Override
    public void setNewsArrayList(ArrayList<News> aL) {
        newsArrayList = aL;
        newsArrayMaxindex = newsArrayList.size() - 1;
        textViewNews.setVisibility(View.VISIBLE);
        textViewNews.setText(newsArrayList.get(0).toString());
        new GetImage(MainActivity.this).execute(newsArrayList.get(0).getUrlToImage());
    }

    @Override
    public void setImage(Bitmap bitmap) {
        iVNewsImage.setImageBitmap(bitmap);
    }

}
