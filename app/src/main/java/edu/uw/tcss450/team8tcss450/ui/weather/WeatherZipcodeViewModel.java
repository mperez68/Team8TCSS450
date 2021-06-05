package edu.uw.tcss450.team8tcss450.ui.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.HashMap;
import java.util.Map;

import edu.uw.tcss450.team8tcss450.MainActivity;
import edu.uw.tcss450.team8tcss450.R;

/**
 * View model containing the current validated lat/long coordinates
 * in which weather data has been successful retrieved from a weather
 * API for the said coordinates. This is used by all weather fragments.
 *
 * @author Brandon Kennedy
 * @version 4 June 2021
 */
public class WeatherZipcodeViewModel extends ViewModel {

    // The city that's localized to the zipcode
    private static String mCity;

    // The latitude coordinates that are within the zipcode
    private static String mLatitude;

    // The longitude coordinates that are within the zipcode
    private static String mLongitude;

    // The hash map of icon-id/icon-image pairs
    private static HashMap<String, Integer> weatherIcons;

    /**
     * Constructor for the WeatherZipcodeViewModel object.
     *
     * @param latitude the latitude coordinates in the zipcode
     * @param longitude the longitude coordinates in the zipcode
     */
    private WeatherZipcodeViewModel(String latitude, String longitude) {
        this.mCity = "";
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.weatherIcons = createWeatherIconHashMap();
    }


    /**
     * Inner factory class responsible for instantiating WeatherZipcodeViewModel objects
     *
     * @author Brandon Kennedy
     * @version 4 June 2021
     */
    public static class WeatherZipcodeViewModelFactory implements ViewModelProvider.Factory {

        // The latitude coordinates in the zipcode that results from the search query
        private static String latitude;

        // The longitude coordinates in the zipcode that results from the search query
        private static String longitude;

        /**
         * Constructor for the WeatherZipcodeViewModelFactory
         *
         * zipcode the queried zipcode
         * city the city associated with the queried zipcode
         */
        public WeatherZipcodeViewModelFactory(//String city,
                                              String latitude, String longitude) {
            Log.v("WeatherZipcodeViewModel", "The view model for WeatherZipcode is created");
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == WeatherZipcodeViewModel.class) {
                return (T) new WeatherZipcodeViewModel(latitude, longitude);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + WeatherZipcodeViewModel.class);
        }

    }

    /**
     * Return the city in the validated zipcode
     * @return the city
     */
    public String getCity() {
        return this.mCity;
    }

    /**
     * Return the latitude coordinates in the validated zipcode
     * @return the latitude coordinates
     */
    public String getLatitude() {
        return this.mLatitude;
    }

    /**
     * Return the longitude coordinates in the validated zipcode
     * @return the longitude coordinates
     */
    public String getLongitude() {
        return this.mLongitude;
    }

    /**
     * Set the city name in which came with the data from a validated zipcode
     *
     * @param city the city that goes along with the validated zipcode
     */
    public void setCity(String city) {
        this.mCity = city;
    }

    /**
     * Set the latitude/longitude in which came with the data from a validated zipcode
     *
     * @param latitude the latitude in which came with the data from a validated zipcode
     * @param longitude the latitude in which came with the data from a validated zipcode
     */
    public void setLocation(String latitude, String longitude) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    /**
     * Create the hash map of weather icon ids that each map to a unique weather
     * outlook icon in the drawable folder.
     * @return the hash map of icon-id/icon image pairs
     */
    private HashMap<String, Integer> createWeatherIconHashMap() {
        HashMap<String, Integer> iconMap = new HashMap<>();
        iconMap.put("2_day", R.drawable.ic_weather_icon_thunderstorm_with_rain_day);
        iconMap.put("2_night", R.drawable.ic_weather_icon_thunderstorm_with_rain_night);
        iconMap.put("23_day", R.drawable.ic_weather_icon_thunderstorm_with_drizzle_day);
        iconMap.put("23_night", R.drawable.ic_weather_icon_thunderstorm_with_drizzle_night);
        iconMap.put("3_day", R.drawable.ic_weather_icon_drizzle_day);
        iconMap.put("3_night", R.drawable.ic_weather_icon_drizzle_night);
        iconMap.put("5_day", R.drawable.ic_weather_icon_rain_day);
        iconMap.put("5_night", R.drawable.ic_weather_icon_rain_night);
        iconMap.put("6_day", R.drawable.ic_weather_icon_snow_day);
        iconMap.put("6_night", R.drawable.ic_weather_icon_snow_night);
        iconMap.put("61_day", R.drawable.ic_weather_icon_sleet_day);
        iconMap.put("61_night", R.drawable.ic_weather_icon_sleet_night);
        iconMap.put("7_day", R.drawable.ic_weather_icon_atmosphere_day);
        iconMap.put("7_night", R.drawable.ic_weather_icon_atmosphere_night);
        iconMap.put("8_day", R.drawable.ic_weather_icon_mostly_cloudy_day);
        iconMap.put("8_night", R.drawable.ic_weather_icon_mostly_cloudy_night);
        iconMap.put("800_day", R.drawable.ic_weather_icon_clear_day);
        iconMap.put("800_night", R.drawable.ic_weather_icon_clear_night);
        iconMap.put("801_day", R.drawable.ic_weather_icon_partly_cloudy_day);
        iconMap.put("801_night", R.drawable.ic_weather_icon_partly_cloudy_night);
        iconMap.put("804_day", R.drawable.ic_weather_icon_cloudy_day);
        iconMap.put("804_night", R.drawable.ic_weather_icon_cloudy_night);
        iconMap.put("9_day", R.drawable.ic_weather_icon_unknown_day);
        iconMap.put("9_night", R.drawable.ic_weather_icon_unknown_night);
        return iconMap;
    }

    /**
     * Get the weather icon resource id that is used to reference
     * a unique weather outlook icon in the drawable folder
     *
     * @param iconId the icon id retrieved from a weather JSON object
     * @param iconCode the icon code retrieved from a weather JSON object
     * @return
     */
    public Integer getWeatherIconResId(final String iconId,
                                       final String iconCode) {
        String dayPeriod = "";
        if (iconCode.endsWith("d")) {
            dayPeriod = "_day";
        } else {
            dayPeriod = "_night";
        }

        if (weatherIcons.containsKey(iconId.substring(0,1) + dayPeriod)) {
            if (weatherIcons.containsKey(iconId.substring(0,2) + dayPeriod)) {
                return weatherIcons.get(iconId.substring(0,2) + dayPeriod);
            } else if (weatherIcons.containsKey(iconId + dayPeriod)) {
                return weatherIcons.get(iconId + dayPeriod);
            } else {
                return weatherIcons.get(iconId.substring(0,1) + dayPeriod);
            }
        } else {
            return weatherIcons.get("9_day");
        }
    }

}
