package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import android.app.Application;
import android.graphics.Bitmap;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * The view model that holds information to be displayed
 * on the 10-day weather forecast fragment
 *
 * @author Brandon Kennedy
 * @version 25 May 2021
 */
public class WeatherDayPredictionViewModel extends AndroidViewModel {

    private final static int POSTS = 10;

    // The encapsulated data and statistics of the 10-day weather forecast
    private MutableLiveData<List<WeatherDayPostInfo>> mWeatherDayPostList;

    // The saved zipcode for this WeatherDayPredictionViewModel
    //private String mZipcode;

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

        //this.mZipcode = "";
        mLatitude = "";
        mLongitude = "";

        mWeatherDayPostList = new MutableLiveData<>();
        mWeatherDayPostList.setValue(new ArrayList<>());
    }

    public String getLatitude() {
        return this.mLatitude;
    }

    public String getLongitude() {
        return this.mLongitude;
    }

    public void setLatLongCoordinates(final String latitude,
                                      final String longitude) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    /**
     * Return the saved zipcode for the daily weather forecast list
     * @return the saved current weather zipcode
    public String getZipcode() {
        return this.mZipcode;
    }
     */

    /**
     * Set the zipcode for the daily weather forecast list
     * @param zipcode the zipcode
    public void setZipcode(final String zipcode) {
        this.mZipcode = zipcode;
    }
     */

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
                                                   final String longitude) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        List<String> outlookIconCodes = new ArrayList<>();
        try {
            JSONObject root = result;
            if (root.has(getString.apply(R.string.keys_json_weatherdailyprediction_data))) {
                JSONArray data =
                    root.getJSONArray(getString.apply(
                        R.string.keys_json_weatherdailyprediction_data));
                mWeatherDayPostList.getValue().clear();
                for (int i = 0; i < POSTS; i++) {
                    JSONObject dailyInterval = data.getJSONObject(i);
                    String outlook = dailyInterval.getJSONObject(getString.apply(
                        R.string.keys_json_weatherdailyprediction_weather)).getString(
                            getString.apply(R.string.keys_json_weatherdailyprediction_description));
                    String highTemp = String.valueOf(dailyInterval.getInt(getString.apply(
                        R.string.keys_json_weatherdailyprediction_hightemp)));
                    String lowTemp = String.valueOf(dailyInterval.getInt(getString.apply(
                            R.string.keys_json_weatherdailyprediction_lowtemp)));
                    WeatherDayPostInfo dayPost =
                        new WeatherDayPostInfo.WeatherDayInfoBuilder(
                            dailyInterval.getString(getString.apply(
                                R.string.keys_json_weatherdailyprediction_validdate)))
                        .addOutlook(outlook)
                        .addHighTemperature(highTemp)
                        .addLowTemperature(lowTemp)
                        .build();
                    if (!mWeatherDayPostList.getValue().contains(dayPost)) {
                        mWeatherDayPostList.getValue().add(dayPost);
                    }
                    String outlookIconCode = dailyInterval.getJSONObject(getString.apply(
                        R.string.keys_json_weatherdailyprediction_weather)).getString(
                            getString.apply(R.string.keys_json_weatherdailyprediction_icon));
                    outlookIconCodes.add(outlookIconCode);
                }
            } else {
                Log.e("ERROR!", "No daily forecast data");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        if (outlookIconCodes.size() == POSTS && mWeatherDayPostList.getValue().size() == POSTS) {
            String iconCode = outlookIconCodes.remove(0);
            this.connectToOutlookIcon(iconCode, outlookIconCodes, latitude, longitude);
        }
    }

    /**
     * If the connection to the WeatherBit API is successful,
     * use the image that comes through this connection to be
     * stored to the 10-day weather forecast predictions list
     *
     * @param response the bitmap outlook icon retrieved from WeatherBit API
     * @param outlookIconCodes the list of icon codes retrieved from the daily JSONObject file
     */
    private void handleResultFromOutlookIconConnect(final Bitmap response,
                                                    final List<String> outlookIconCodes,
                                                    final String latitude,
                                                    final String longitude) {
        mWeatherDayPostList.getValue()
            .get(POSTS - outlookIconCodes.size() - 1).setOutlookIcon(response);
        if (!outlookIconCodes.isEmpty()) {
            String iconCode = outlookIconCodes.remove(0);
            this.connectToOutlookIcon(iconCode, outlookIconCodes, latitude, longitude);
        } else {
            mWeatherDayPostList.setValue(mWeatherDayPostList.getValue());
            this.setLatLongCoordinates(latitude, longitude);
        }
    }

    /**
     * Connect to the WeatherBit API to obtain the needed data for the 10-day forecast fragment
     *
     * @params zipcode the successfully validated zipcode used to obtain daily forecast weather information
     */
    public void connectToWeatherBit(//final String zipcode
                                        final String latitude,
                                        final String longitude) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/daily";
        Log.i("WeatherDayPredictionViewModel.java", "Connecting to OpenWeatherMap API for Daily Forecast Weather");
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                result -> {
                    this.handleResultFromWeatherBitConnect(result, latitude, longitude);
                },
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", "37cb34eeb8df469796e2a87f43c7f9be");
//                headers.put("zipcode", zipcode);
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
     * Connect to the WeatherBit API via Heroku app to obtain the
     * current weather outlook icon bitmap object
     *
     * @param iconCode the code used to get the specified weather icon image
     */
    public void connectToOutlookIcon(final String iconCode,
                                     final List<String> outlookIconCodes,
                                     final String latitude,
                                     final String longitude) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/icon/weatherbit";
        Request request = new ImageRequest(
                url,
                result -> {
                    this.handleResultFromOutlookIconConnect(result, outlookIconCodes, latitude, longitude);
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
