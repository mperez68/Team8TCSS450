package edu.uw.tcss450.team8tcss450.ui.weather;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * View model containing the current validated zipcode in which
 * weather data has been successful retrieved from a weather API
 * for the said zipcode. This is used by all weather fragments.
 *
 * @author Brandon Kennedy
 * @version 19 May 2021
 */
public class WeatherZipcodeViewModel extends ViewModel {

    // The successfully validated zipcode
    private static String mZipcode;

    // The city that's localized to the zipcode
    private static String mCity;

    /**
     * Constructor for the WeatherZipcodeViewModel object.
     *
     * @param zipcode the zipcode
     * @param city the city in the zipcode
     */
    private WeatherZipcodeViewModel(String zipcode, String city) {
        this.mZipcode = zipcode;
        this.mCity = city;
    }

    /**
     * Inner factory class responsible for instantiating WeatherZipcodeViewModel objects
     *
     * @author Brandon Kennedy
     * @version 16 May 2021
     */
    public static class WeatherZipcodeViewModelFactory implements ViewModelProvider.Factory {

        // The zipcode that is used for search query
        private static String zipcode;

        // The city in the zipcode that results from the search query
        private static String city;


        /**
         * Constructor for the WeatherZipcodeViewModelFactory
         *
         * @param zipcode the queried zipcode
         * @param city the city associated with the queried zipcode
         */
        public WeatherZipcodeViewModelFactory(String zipcode, String city) {
            this.zipcode = zipcode;
            this.city = city;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == WeatherZipcodeViewModel.class) {
                return (T) new WeatherZipcodeViewModel(zipcode, city);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + WeatherZipcodeViewModel.class);
        }

    }

    /**
     * Return the validated zipcode
     * @return the validated zipcode
     */
    public String getZipcode() {
        return this.mZipcode;
    }

    /**
     * Return the city in the validated zipcode
     * @return the city
     */
    public String getCity() {
        return this.mCity;
    }

    /**
     * Set the zipcode that has been successfully validated
     *
     * @param zipcode the zipcode that's been validated
     */
    public void setZipcode(String zipcode) {
        this.mZipcode = zipcode;
    }

    /**
     * Set the city name in which came with the data from a validated zipcode
     *
     * @param city the city that goes along with the validated zipcode
     */
    public void setCity(String city) {
        this.mCity = city;
    }

}
