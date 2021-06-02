package edu.uw.tcss450.team8tcss450.ui.weather;

import android.location.Location;
import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
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

/**
 * View model containing the current validated zipcode in which
 * weather data has been successful retrieved from a weather API
 * for the said zipcode. This is used by all weather fragments.
 *
 * @author Brandon Kennedy
 * @version 30 May 2021
 */
public class WeatherZipcodeViewModel extends ViewModel {

    // The successfully validated zipcode
    //private static String mZipcode;

    // The city that's localized to the zipcode
    private static String mCity;

    // The latitude coordinates that are within the zipcode
    private static String mLatitude;

    // The longitude coordinates that are within the zipcode
    private static String mLongitude;

    /**
     * Constructor for the WeatherZipcodeViewModel object.
     *
     * zipcode the zipcode
     * city the city in the zipcode
     * @param latitude the latitude coordinates in the zipcode
     * @param longitude the longitude coordinates in the zipcode
     */
    private WeatherZipcodeViewModel(//String city,
                                    String latitude, String longitude) {
        //this.mZipcode = zipcode;
        this.mCity = "";
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }


    /**
     * Inner factory class responsible for instantiating WeatherZipcodeViewModel objects
     *
     * @author Brandon Kennedy
     * @version 30 May 2021
     */
    public static class WeatherZipcodeViewModelFactory implements ViewModelProvider.Factory {

        // The zipcode that is used for search query
        //private static String zipcode;

        // The city in the zipcode that results from the search query
        private static String city;

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
            //this.zipcode = zipcode;
            //this.city = city;
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
     * Return the validated zipcode
     * @return the validated zipcode
    public String getZipcode() {
        return this.mZipcode;
    }
     */

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
     * Set the zipcode that has been successfully validated
     *
     * @param zipcode the zipcode that's been validated
    public void setZipcode(String zipcode) {
        this.mZipcode = zipcode;
    }
     */

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






}
