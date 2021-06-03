package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * The view model that holds information to be displayed
 * on the 24-hour weather forecast fragment
 *
 * @author Brandon Kennedy
 * @version 2 June 2021
 */
public class WeatherHourPredictionViewModel extends AndroidViewModel {

    // The number of weather hour posts in the weather hour prediction fragment
    private final static int POSTS = 24;

    // The encapsulated data and statistics of the 24-hour weather forecast
    private MutableLiveData<List<WeatherHourPostInfo>> mWeatherHourPostList;

    // The latitude and longitude coordinates in which
    // the hourly prediction fragment displays about
    private String mLatitude;
    private String mLongitude;

    /**
     * Construct the view model of the 24-hour weather forecast
     *
     * @param application the application that must be created for this view model to be applied to
     */
    public WeatherHourPredictionViewModel(@NonNull Application application) {
        super(application);
        Log.v("WeatherHourPredictionViewModel", "The view model for WeatherHourPredictionList is created");

        this.mLatitude = "";
        this.mLongitude = "";

        mWeatherHourPostList = new MutableLiveData<>();
        mWeatherHourPostList.setValue(new ArrayList<>());
    }

    /**
     * Get the latitude coordinates saved in this view model
     * @return the saved latitude coordinates
     */
    public String getLatitude() {
        return this.mLatitude;
    }

    /**
     * Get the longitude coordinates saved in this view model
     * @return the saved longitude coordinates
     */
    public String getLongitude() {
        return this.mLongitude;
    }

    /**
     * Set the latitude and longitude coordinates for this view model
     *
     * @param latitude the latitude coordinates
     * @param longitude the longitude coordinates
     */
    public void setLatLongCoordinates(final String latitude,
                                      final String longitude) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    /**
     * Add an observer for the hourly forecast post list of this view model
     *
     * @param owner the owner of the lifecycle that controls this observer
     * @param observer the official observer for the hourly forecast post list
     */
    public void addWeatherHourListObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super List<WeatherHourPostInfo>> observer) {
        mWeatherHourPostList.observe(owner, observer);
    }

    /**
     * Clear the list of hourly forecast cards in the list,
     * when the user exits from the 24-hour forecast fragment.
     */
    public void clearList() {
        mWeatherHourPostList.getValue().clear();
        mWeatherHourPostList = new MutableLiveData<>();
        mWeatherHourPostList.setValue(new ArrayList<>());
    }

    /**
     * In case an error occurs in connecting to and getting data from an API,
     * display a message acknowledging a connection or state error.
     *
     * @param error the instance of the volley error
     */
    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    /**
     * If the connection to the OpenWeatherMap API is successful,
     * use the data that comes through this connection to store
     * into to the 24-hour weather forecast predictions list
     *
     * @param result the JSON file and data containing the need info for the 24-hour forecast fragment
     */
    private void handleResultFromOpenWeatherMap(final JSONObject result, final String latitude, final String longitude) {
        Log.i("WeatherHourPredictionViewModel.java", "We now handle response to JSON Object retrieved from WeatherBit API");
        Log.i("WeatherHourPredictionViewModel.java", result.toString());
        IntFunction<String> getString =
                getApplication().getResources()::getString;

        List<String> outlookIconCodes = new ArrayList<>();
        try {
            JSONObject root = result;
            if (root.has(getString.apply(R.string.keys_json_weatherhourlyprediction_hourly))) {
                JSONArray hourly =
                    root.getJSONArray(getString.apply(
                        R.string.keys_json_weatherhourlyprediction_hourly));
                mWeatherHourPostList.getValue().clear();
                for (int i = 0; i < POSTS; i++) {
                    JSONObject hourInterval = hourly.getJSONObject(i);

                    Date date = new Date();
                    date.setTime(hourInterval.getLong(getString.apply(
                            R.string.keys_json_weatherhourlyprediction_dt)) * 1000L);
                    SimpleDateFormat hourFormat = new SimpleDateFormat("h:mm a");
                    String formattedHour = hourFormat.format(date);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
                    String formattedDate = dateFormat.format(date);

                    String main = hourInterval.getJSONArray(getString.apply(
                        R.string.keys_json_weatherhourlyprediction_weather)).getJSONObject(0)
                            .getString(getString.apply(R.string.keys_json_weatherhourlyprediction_main));
                    String temp = String.valueOf(hourInterval.getInt(
                            getString.apply(R.string.keys_json_weatherhourlyprediction_temp)));

                    WeatherHourPostInfo hourPost =
                        new WeatherHourPostInfo.WeatherHourInfoBuilder(
                            formattedDate,
                            formattedHour
                        )
                        .addOutlook(main)
                        .addTemperature(temp)
                        .build();

                    if (!mWeatherHourPostList.getValue().contains(hourPost)) {
                        mWeatherHourPostList.getValue().add(hourPost);
                    }

                    String outlookIcon = String.valueOf(hourInterval.getJSONArray(
                        getString.apply(R.string.keys_json_weatherhourlyprediction_weather))
                            .getJSONObject(0).getString(getString.apply(R.string.keys_json_weatherhourlyprediction_icon)));

                    outlookIconCodes.add(outlookIcon);
                    Log.v("WeatherHourPredictionViewModel.java",
                            hourPost.getDate() + ", " + hourPost.getTime() + ", "
                                    + hourPost.getOutlook() + ", " + hourPost.getTemperature() + ", "
                                        + outlookIcon);
                }
            } else {
                Log.e("ERROR!", "No hourly forecast data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        if (outlookIconCodes.size() == POSTS && mWeatherHourPostList.getValue().size() == POSTS) {
            String iconCode = outlookIconCodes.remove(0);
            this.connectToOutlookIcon(iconCode, outlookIconCodes, latitude, longitude);
        }
    }

    /**
     * If connection to the OpenWeatherMap API is successful, handle the
     * image retrieved from that API and store it with the outlook
     * icon list in the MutableLiveData field
     *
     * @param response the bitmap retrieved from the OpenWeatherMap API
     */
    private void handleResultFromOpenWeatherMapIconRetrieval(final Bitmap response,
                                                             final List<String> outlookIconCodes,
                                                             final String latitude,
                                                             final String longitude) {
        Log.i("WeatherHourPredictionViewModel", "Got outlook icon from API");
        mWeatherHourPostList.getValue()
                .get(POSTS - outlookIconCodes.size() - 1).setOutlookIcon(response);
        if (!outlookIconCodes.isEmpty()) {
            String iconCode = outlookIconCodes.remove(0);
            this.connectToOutlookIcon(iconCode, outlookIconCodes, latitude, longitude);
        } else {
            mWeatherHourPostList.setValue(mWeatherHourPostList.getValue());
            Log.i("WeatherHourPredictionViewModel", "Got all needed outlook icons");
            this.setLatLongCoordinates(latitude, longitude);
        }
    }

    /**
     * Connect to the OpenWeatherMap API to obtain the needed data for
     * the 24-hour forecast fragment. This connection is to happen only
     * after a prior successful connection to the WeatherBit API that
     * gets the latitude and longitude coordinates on a validated zipcode
     * needed for connection to the OpenWeatherMap API.
     *
     * @params latitude the latitude coordinates of the validated zipcode
     * @params longitude the longitude coordinates of the validated zipcode
     */
    public void connectToOpenWeatherMap(final String latitude,
                                        final String longitude) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/hourly";
        Log.i("WeatherHourPredictionViewModel.java", "Connecting to OpenWeatherMap API for Hourly Forecast Weather");
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                result -> {
                    this.handleResultFromOpenWeatherMap(result, latitude, longitude);
                },
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", "6543ce89bd26ded32186bae89a6a071e");
                headers.put("latitude", latitude);
                headers.put("longitude", longitude);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Connect to the OpenWeatherMap API via Heroku app to obtain the
     * current weather outlook icon bitmap object
     *
     * @param iconCode the code used to get the specified weather icon image
     */
    public void connectToOutlookIcon(final String iconCode,
                                     final List<String> outlookIconCodes,
                                     final String latitude,
                                     final String longitude) {
        Log.i("WeatherHourPredictionViewModel.connectToOutlookIcon", "About to connect to get outlook icon.");
        String url = "https://team8-tcss450-app.herokuapp.com/weather/icon/openweathermap";
        Request request = new ImageRequest(
                url,
                result -> {
                    this.handleResultFromOpenWeatherMapIconRetrieval(result, outlookIconCodes, latitude, longitude);
                },
                60,
                60,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();

                headers.put("icon_code", iconCode);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

}
