package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
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
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherMainViewModel;

/**
 * The view model that holds information to be displayed
 * on the 24-hour weather forecast fragment
 *
 * @author Brandon Kennedy
 * @version 19 May 2021
 */
public class WeatherHourPredictionViewModel extends AndroidViewModel {

    // The encapsulated data and statistics of the 24-hour weather forecast
    private MutableLiveData<List<WeatherHourPostInfo>> mWeatherHourPostList;

    // The JSON object that is retrieved from the WeatherBit API
    private MutableLiveData<JSONObject> mWeatherBitResponse;

    // The JSON object that is retrieved from the OpenWeatherMap API
    //private MutableLiveData<JSONObject> mOpenWeatherMapResponse;


    /**
     * Construct the view model of the 24-hour weather forecast
     *
     * @param application the application that must be created for this view model to be applied to
     */
    public WeatherHourPredictionViewModel(@NonNull Application application) {
        super(application);
        mWeatherHourPostList = new MutableLiveData<>();
        mWeatherHourPostList.setValue(new ArrayList<>());

        mWeatherBitResponse = new MutableLiveData<>();
        mWeatherBitResponse.setValue(new JSONObject());
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
     * Add an observer for the response to the retrieval of data from an API
     *
     * @param owner the owner of the lifecycle that controls this observer
     * @param observer the official observer for the data from an API
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mWeatherBitResponse.observe(owner, observer);
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
     * If this fragment is successful in connecting to an API,
     * use the data that comes through this connection to implement
     * to the 24-hour weather forecast predictions list
     *
     * @param result the JSON file and data containing the need info for the 24-hour forecast fragment
     */
    private void handleResult(final JSONObject result) {
        Log.i("WeatherHourPredictionViewModel.java", "We now handle response to JSON Object retrieved from WeatherBit API");
        Log.i("WeatherHourPredictionViewModel.java", result.toString());
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            JSONObject root = result;
            if (root.has(getString.apply(R.string.keys_json_weatherhourlyprediction_hourly))) {
                JSONArray hourly =
                    root.getJSONArray(getString.apply(
                        R.string.keys_json_weatherhourlyprediction_hourly));
                mWeatherHourPostList.getValue().clear();
                for (int i = 0; i < 24; i++) {
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
                    Log.v("WeatherHourPredictionViewModel.java",
                            hourPost.getDate() + ", " + hourPost.getTime() + ", "
                            + hourPost.getOutlook() + ", " + hourPost.getTemperature());
                    if (!mWeatherHourPostList.getValue().contains(hourPost)) {
                        mWeatherHourPostList.getValue().add(hourPost);
                    }
                }
            } else {
                Log.e("ERROR!", "No hourly forecast data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mWeatherHourPostList.setValue(mWeatherHourPostList.getValue());
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
     * Return whether or not the list of hourly weather information is empty
     * @return the state of whether the hourly weather list is empty or not
     */
    public boolean isEmpty() {
        return mWeatherHourPostList.getValue().isEmpty();
    }

    /**
     * Connect to the WeatherBit API to obtain primarily the
     * latitude and longitude coordinates of the validated zipcode
     *
     * @params zipcode the zipcode that will be used to find its lat/lon coordinates
     */
    public void connectToWeatherBit(final String zipcode) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/zipcode_to_lat_long";
        Log.i("WeatherHourPredictionViewModel.java", "Connecting to WeatherBit API for latitude and longitude coordinates from zipcode");

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                mWeatherBitResponse::setValue,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", "37cb34eeb8df469796e2a87f43c7f9be");
                headers.put("zipcode", zipcode);
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
        //mZipcode.setZipcode(zipcode);
        String url = "https://team8-tcss450-app.herokuapp.com/weather/hourly";
        Log.i("WeatherHourPredictionViewModel.java", "Connecting to OpenWeatherMap API for Hourly Forecast Weather");
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult,
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

}
