package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * The view model that holds information to be displayed
 * on the 10-day weather forecast fragment
 *
 * @author Brandon Kennedy
 * @version 4 June 2021
 */
public class WeatherDayPredictionViewModel extends AndroidViewModel {

    // The number of weather day posts in the weather day prediction fragment
    private final static int POSTS = 10;

    // The encapsulated data and statistics of the 10-day weather forecast
    private MutableLiveData<List<WeatherDayPostInfo>> mWeatherDayPostList;

    // The latitude and longitude coordinates in which
    // the hourly prediction fragment displays about
    private String mLatitude;
    private String mLongitude;

    /**
     * Construct the view model of the 10-day weather forecast
     *
     * @param application the application that must be created for this view model to be applied to
     */
    public WeatherDayPredictionViewModel(@NonNull Application application) {
        super(application);
        Log.v("WeatherDayPredictionViewModel", "The view model for WeatherDayPredictionList is created");

        mLatitude = "";
        mLongitude = "";

        mWeatherDayPostList = new MutableLiveData<>();
        mWeatherDayPostList.setValue(new ArrayList<>());
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
     * Add an observer for this view model
     *
     * @param owner the owner of the lifecycle that controls this observer
     * @param observer the official observer of this view model
     */
    public void addWeatherDayListObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super List<WeatherDayPostInfo>> observer) {
        mWeatherDayPostList.observe(owner, observer);
    }

    /**
     * Clear the list of hourly forecast cards in the list,
     * when the user exits from the 24-hour forecast fragment.
     */
    public void clearList() {
        mWeatherDayPostList.getValue().clear();
        mWeatherDayPostList = new MutableLiveData<>();
        mWeatherDayPostList.setValue(new ArrayList<>());
    }

    /**
     * Return whether or not the list of daily weather information is empty
     * @return the state of whether the daily weather list is empty or not
     */
    public boolean isEmpty() {
        return mWeatherDayPostList.getValue().isEmpty();
    }

    /**
     * In case an error occurs in the connection in which this class depends on,
     * take appropriate actions to handle this error, depending on the nature and type of error
     *
     * @param error the instance of the volley error
     */
    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    /**
     * If the connection to the WeatherBit API is successful,
     * use the data that comes through this connection to store
     * needed data to the 10-day weather forecast predictions list
     *
     * @param result the JSON file and data containing the need info for the 10-day forecast fragment
     */
    private void handleResultFromWeatherBitConnect(final JSONObject result,
                                                   final String latitude,
                                                   final String longitude,
                                                   WeatherZipcodeViewModel model) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            JSONObject root = result;
            if (root.has(getString.apply(R.string.keys_json_weatherdailyprediction_data))) {
                JSONArray data =
                    root.getJSONArray(getString.apply(
                        R.string.keys_json_weatherdailyprediction_data));
                mWeatherDayPostList.getValue().clear();
                for (int i = 0; i < POSTS; i++) {
                    JSONObject dailyInterval = data.getJSONObject(i);

                    Calendar calendar = Calendar.getInstance();
                    int calendarDay = calendar.get(Calendar.DAY_OF_WEEK);
                    calendar.set(Calendar.DAY_OF_WEEK, calendarDay + i + 1);
                    Date date = calendar.getTime();
                    String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
                    String day = dayOfWeek.toUpperCase() + " " + dailyInterval.getString(getString.apply(
                            R.string.keys_json_weatherdailyprediction_validdate)).substring(5);

                    String outlook = dailyInterval.getJSONObject(getString.apply(
                        R.string.keys_json_weatherdailyprediction_weather)).getString(
                            getString.apply(R.string.keys_json_weatherdailyprediction_description));

                    String outlookIconCode = dailyInterval.getJSONObject(getString.apply(
                        R.string.keys_json_weatherdailyprediction_weather)).getString(
                            getString.apply(R.string.keys_json_weatherdailyprediction_icon));
                    String outlookIconId = String.valueOf(dailyInterval.getJSONObject(getString.apply(
                            R.string.keys_json_weatherdailyprediction_weather)).getInt(
                            getString.apply(R.string.keys_json_weatherdailyprediction_code)));
                    Log.d("WeatherDayPredictionViewModel.handleResultFromWeatherBitConnect",
                            "outlookIconId = " + outlookIconId + ", outlookIconCode = " + outlookIconCode);
                    Integer outlookIconResId = model.getWeatherIconResId(outlookIconId, outlookIconCode);

                    String highTemp = String.valueOf(dailyInterval.getInt(getString.apply(
                        R.string.keys_json_weatherdailyprediction_hightemp)));

                    String lowTemp = String.valueOf(dailyInterval.getInt(getString.apply(
                            R.string.keys_json_weatherdailyprediction_lowtemp)));

                    WeatherDayPostInfo dayPost =
                        new WeatherDayPostInfo.WeatherDayInfoBuilder(day)
                        .addOutlook(outlook)
                        .addOutlookIconResId(outlookIconResId)
                        .addHighTemperature(highTemp)
                        .addLowTemperature(lowTemp)
                        .build();
                    if (!mWeatherDayPostList.getValue().contains(dayPost)) {
                        mWeatherDayPostList.getValue().add(dayPost);
                    }
                }
            } else {
                Log.e("ERROR!", "No daily forecast data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        this.setLatLongCoordinates(latitude, longitude);
        mWeatherDayPostList.setValue(mWeatherDayPostList.getValue());
    }

    /**
     * Connect to the WeatherBit API to obtain the needed data for the 10-day forecast fragment
     *
     * @params latitude the latitude coordinates used to get data from
     * @params longitude the longitude coordinates used to get data from
     * @model the WeatherZipcodeViewModel passed from a fragment
     */
    public void connectToWeatherBit(final String latitude,
                                    final String longitude,
                                    WeatherZipcodeViewModel model) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/daily";
        Log.i("WeatherDayPredictionViewModel.java", "Connecting to OpenWeatherMap API for Daily Forecast Weather");
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                result -> {
                    this.handleResultFromWeatherBitConnect(result, latitude, longitude, model);
                },
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", "37cb34eeb8df469796e2a87f43c7f9be");
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
