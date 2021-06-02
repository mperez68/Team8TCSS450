package edu.uw.tcss450.team8tcss450.ui.weather.current;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherCurrentBinding;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * The view model for WeatherCurrentFragment that handles
 * a queried zipcode and determines whether or not it is
 * valid.  If it is valid, a connection is made to a
 * weather API to retrieve current weather condition data.
 *
 * @author Brandon Kennedy
 * @version 25 May 2021
 */
public class WeatherCurrentViewModel extends AndroidViewModel {

    // The encapsulated data and statistics of the current weather conditions
    private MutableLiveData<WeatherCurrentInfo> mWeatherCurrentInfo;

    // The saved zipcode for this WeatherCurrentViewModel
    //private String mZipcode;

    private String mLatitude;

    private String mLongitude;

    /**
     * Construct the view model
     */
    public WeatherCurrentViewModel(@NonNull Application application) {
        super(application);
        Log.v("WeatherCurrentViewModel", "The view model for WeatherCurrent is created");

        //mZipcode = "";

        mLatitude = "";

        mLongitude = "";

        mWeatherCurrentInfo = new MutableLiveData<>();
        mWeatherCurrentInfo.setValue(
                new WeatherCurrentInfo.WeatherCurrentInfoBuilder("").build()
        );
    }

    /**
     * Return the saved zipcode for the current weather fragment
     * @return the saved current weather zipcode
    public String getZipcode() {
        return this.mZipcode;
    }
     */

    /**
     * Set the zipcode for the current weather fragment
     * zipcode the zipcode
    public void setZipcode(final String zipcode) {
        this.mZipcode = zipcode;
    }
     */


    public String getLatitude() {
        return this.mLatitude;
    }

    public String getLongitude() {
        return this.mLongitude;
    }

    public void setLatLongCoordindates(final String latitude, final String longitude) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }


    /**
     * Return the saved current weather information
     * @return the saved current weather information
     */
    public WeatherCurrentInfo getWeatherInfo() {
        return this.mWeatherCurrentInfo.getValue();
    }

    /**
     * If connection to the OpenWeatherMap API is successful,
     * handle the JSONObject retrieved from that API through
     * validation and information retrieval.
     *
     * @param response the JSONObject retrieved as a successful response from the OpenWeatherMap API
     */
    private void handleResultFromOpenWeatherMap(final JSONObject response,
                                                //final String zipcode,
                                                final String latitude,
                                                final String longitude,
                                                final FragmentWeatherCurrentBinding binding) {
        if (validateResponseFromOpenWeatherMap(response)) {
            //this.setZipcode(zipcode);
            this.setLatLongCoordindates(latitude, longitude);

            // Get all needed information from JSONObject file and place it in WeatherCurrentInfo field
            getInformationFromOpenWeatherMap(response);

            try {
                // Get needed outlook icon from JSONObject file in order
                // to be able to connect to the URL to retrieve needed outlook icon
                IntFunction<String> getString =
                        getApplication().getResources()::getString;
                String outlookIconCode = response.getJSONArray(
                        getString.apply(R.string.keys_json_weathercurrent_weather))
                        .getJSONObject(0).getString(getString.apply(R.string.keys_json_weathercurrent_icon));
                this.connectToOutlookIcon(outlookIconCode, binding);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("ERROR!", e.getMessage());
            }
        } else {
            Log.e("ERROR from WeatherMainViewModel", "JSONObject from OpenWeatherMap is not valid.");
        }
    }

    /**
     * Validate the JSONObject file retrieved from the OpenWeatherMap API
     * to check if contains the needed current weather information and
     * does not connect any errors or error messages.
     *
     * @param response the JSONObject file from the OpenWeatherMap API
     * @return whether the OpenWeatherMap JSONObject contains the needed information or not
     */
    private Boolean validateResponseFromOpenWeatherMap(final JSONObject response) {
        if (response.length() > 0 && !response.has("error")) {
            Log.i("WeatherMainFragment.java", "We have a JSON. Now we open it to look for 'cod' key");
            if (response.has("cod")) {
                Log.i("WeatherMainFragment.java", "JSON object has 'cod' key, 'cod': "
                        + String.valueOf(response.optInt("cod", 400)));
                if ( response.optInt("cod", 400) == 200  ) {
                    return true;
                } else {
                    Log.e("WeatherMainFragment.java",
                            "Looks like cod is not equal to 200. This JSON file has no data.");
                    return false;
                }
            } else {
                Log.d("JSON Response", "JSON Object does not contain 'cod' key");
                return false;
            }
        } else {
            Log.d("JSON Response", "JSON Object contains 'error' key");
            return false;
        }
    }

    /**
     * Retrieve the needed current weather information from the
     * JSONObject file retrieved from a call to the OpenWeatherMap API
     *
     * @param root the JSONObject file from the OpenWeatherMap API
     */
    private void getInformationFromOpenWeatherMap(final JSONObject root) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            Log.i("JSON Response", "JSON response object has no error code key");

            String city = root.getString("name");

            String readTemp = String.valueOf(
                    root.getJSONObject(getString.apply(R.string.keys_json_weathercurrent_main))
                            .getInt(getString.apply(R.string.keys_json_weathercurrent_temp)));

            String readOutlook = String.valueOf(
                    root.getJSONArray(getString.apply(R.string.keys_json_weathercurrent_weather))
                            .getJSONObject(0)
                            .getString(getString.apply(R.string.keys_json_weathercurrent_main)));

            String readHumidity = String.valueOf(
                    root.getJSONObject(getString.apply(R.string.keys_json_weathercurrent_main))
                            .getInt(getString.apply(R.string.keys_json_weathercurrent_humidity))) + "%";

            String windSpeed = String.valueOf(
                    root.getJSONObject(getString.apply(R.string.keys_json_weathercurrent_wind))
                            .getInt(getString.apply(R.string.keys_json_weathercurrent_speed))) + " MPH";
            int windDegree =
                    root.getJSONObject(getString.apply(R.string.keys_json_weathercurrent_wind))
                            .getInt(getString.apply(R.string.keys_json_weathercurrent_deg));
            String windDirection = this.windDirection(windDegree);

            String visibility = String.valueOf(
                    root.getDouble(getString.apply(R.string.keys_json_weathercurrent_visibility)) / 1000) + " Miles";

            WeatherCurrentInfo currentInfo =
                    new WeatherCurrentInfo.WeatherCurrentInfoBuilder(city)
                            .addTemperature(readTemp)
                            .addHumidity(readHumidity)
                            .addOutlook(readOutlook)
                            .addWind(windSpeed,windDirection)
                            .addVisibility(visibility)
                            .build();
            mWeatherCurrentInfo.setValue(currentInfo);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
    }

    /**
     * If connection to the WeatherBit API is successful, handle the
     * image retrieved from that API and store with the current weather
     * info being held in the MutableLiveData field
     *
     * @param response the bitmap retrieved from the WeatherBit API
     */
    public void setOutlookIcon(final Bitmap response) {
        mWeatherCurrentInfo.getValue().setOutlookIcon(response);
    }

    /**
     * If connection to the OpenWeatherMap API to retrieve outlook
     * icon bitmap is successful, then store that icon with current weather info field
     * and finally display all needed statistics and icon on fragment
     *
     * @param response the bitmap outlook icon retrieved from the WeatherBit API URL
     * @param binding the binding of the WeatherMainFragment
     */
    private void handleResultFromOutlookIconConnect(final Bitmap response,
                                                    final FragmentWeatherCurrentBinding binding) {
        this.setOutlookIcon(response);
        this.displayInformation(binding, this.getWeatherInfo());
    }

    /**
     * Display all needed information on the main weather fragment
     *
     * @param binding the binding of the WeatherMainFragment
     * @param currentInfo the encapsulated information about the current weather conditions
     */
    public void displayInformation(final FragmentWeatherCurrentBinding binding,
                                   final WeatherCurrentInfo currentInfo) {
        String readTemp = currentInfo.getTemperature() + "\u00B0F";
        binding.currentHumidityReading.setText(readTemp);

        Bitmap outlookIcon = currentInfo.getOutlookIcon();
        binding.currentOutlook.outlookOutlookIcon.setImageBitmap(outlookIcon);

        String readOutlook = currentInfo.getOutlook();
        binding.currentOutlook.outlookOutlookReading.setText(readOutlook);

        String readHumidity = currentInfo.getHumidity();
        binding.currentHumidityReading.setText(readHumidity);

        String readWind = currentInfo.getWindDirection() + " " +
                currentInfo.getWindSpeed();
        binding.currentWindReading.setText(readWind);

        String readVisibility = currentInfo.getVisibility();
        binding.currentVisibilityReading.setText(readVisibility);
    }

    /**
     * Get the wind direction label depending on degrees
     * relative to a clockwise compass (0 degrees for North, 180 degrees for South)
     *
     * @param degrees the degree amount (0 to 360)
     * @return the compass direction of the wind
     */
    private String windDirection(final int degrees) {
        String[] directions = {
                "N", "NNE", "NE", "ENE",
                "E", "ESE", "SE", "SSE",
                "S", "SSW", "SW", "WSW",
                "W", "WNW", "NW", "NNW"  };
        if (degrees >= 0 && degrees <= 360) {
            return directions[(int) (Math.floor(degrees/22.5) % directions.length)];
        } else {
            return "NaN";
        }
    }

    /**
     * In case attempt to connect to OpenWeatherMap or WeatherBit API
     * and obtain data from it goes wrong, deliver an appropriate
     * handling on this failed attempt
     *
     * @param error the Volley error being handled
     */
    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    /**
     * Connect to the OpenWeatherMap API via Heroku app to obtain
     * current weather data about the queried zipcode in a JSON object
     *
     * zipcode the queried zipcode used to obtain weather data on
     */
    public void connectToOpenWeatherMap(//final String zipcode,
                                        final String latitude,
                                        final String longitude,
                                        final FragmentWeatherCurrentBinding binding) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/current/openweathermap";
        Log.i("WeatherMainViewModel.java", "Connecting to OpenWeatherMap API current weather conditions");
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                result -> {
                    //this.handleResultFromOpenWeatherMap(result, zipcode, model, binding);
                    this.handleResultFromOpenWeatherMap(result, latitude, longitude, binding);
                },
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", "6543ce89bd26ded32186bae89a6a071e");
                //headers.put("zipcode", zipcode);
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
     * current weather outlook icon JSON object
     *
     * @param iconCode the code used to get the specified weather icon image
     * @param binding the binding of the WeatherMainFragment
     */
    public void connectToOutlookIcon(final String iconCode,
                                     final FragmentWeatherCurrentBinding binding) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/icon/openweathermap";
        Request request = new ImageRequest(
                url,
                result -> {
                    this.handleResultFromOutlookIconConnect(result, binding);
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
