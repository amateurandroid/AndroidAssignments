package com.example.androidassignments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    private static final String TAG = "WeatherForecast";
    private ImageView imageViewWeather;
    private TextView textViewCurrentTemp, textViewMinTemp, textViewMaxTemp;
    private ProgressBar progressBar;
    private Spinner citySpinner;
    private String selectedCity = "ottawa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        imageViewWeather = findViewById(R.id.weather_icon);
        textViewCurrentTemp = findViewById(R.id.current_temp);
        textViewMinTemp = findViewById(R.id.min_temp);
        textViewMaxTemp = findViewById(R.id.max_temp);
        progressBar = findViewById(R.id.progress_loading);
        citySpinner = findViewById(R.id.city_spinner);

        // Populate Spinner with Canadian cities
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.canadian_cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        // Set listener for Spinner item selection
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = parent.getItemAtPosition(position).toString().toLowerCase();
                fetchWeatherData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default selection (if none is chosen)
                selectedCity = "ottawa";
                fetchWeatherData();
            }
        });
    }

    private void fetchWeatherData() {
        progressBar.setVisibility(View.VISIBLE);
        String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + selectedCity +
                ",ca&APPID=4da5d88427e5eb4855dde6cd3821c8fc&mode=xml&units=metric";
        new ForecastQuery().execute(weatherUrl);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String currentTemp, minTemp, maxTemp;
        private Bitmap weatherIcon;

        @Override
        protected String doInBackground(String... args) {
            String urlString = args[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("temperature")) {
                            currentTemp = parser.getAttributeValue(null, "value");
                            minTemp = parser.getAttributeValue(null, "min");
                            maxTemp = parser.getAttributeValue(null, "max");
                            publishProgress(25, 50);
                        } else if (tagName.equals("weather")) {
                            String iconName = parser.getAttributeValue(null, "icon");
                            String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";

                            if (!fileExistance(iconName + ".png")) {
                                Log.d(TAG, "Weather image not found locally. Downloading image...");
                                URL imageDownloadUrl = new URL(imageUrl);
                                HttpURLConnection imageConnection = (HttpURLConnection) imageDownloadUrl.openConnection();
                                InputStream imageStream = imageConnection.getInputStream();
                                weatherIcon = BitmapFactory.decodeStream(imageStream);

                                FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                weatherIcon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                                Log.d(TAG, "Weather image downloaded and saved locally.");
                            } else {
                                Log.d(TAG, "Weather image found locally. Loading from storage...");
                                FileInputStream fis = openFileInput(iconName + ".png");
                                weatherIcon = BitmapFactory.decodeStream(fis);
                            }
                            publishProgress(75, 100);
                        }
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error fetching weather data", e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            textViewCurrentTemp.setText("Current: " + currentTemp + "°C");
            textViewMinTemp.setText("Min: " + minTemp + "°C");
            textViewMaxTemp.setText("Max: " + maxTemp + "°C");
            imageViewWeather.setImageBitmap(weatherIcon);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}
