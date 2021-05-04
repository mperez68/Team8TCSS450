package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model that holds information to be displayed
 * on the 24-hour weather forecast fragment
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherHourPredictionViewModel extends AndroidViewModel {

    // The encapsulated data and statistics of the 24-hour weather forecast
    private MutableLiveData<List<WeatherHourPostInfo>> mWeatherHourPostList;

    /**
     * Construct the view model of the 24-hour weather forecast
     *
     * @param application the application that must be created for this view model to be applied to
     */
    public WeatherHourPredictionViewModel(@NonNull Application application) {
        super(application);
        mWeatherHourPostList = new MutableLiveData<>();
        mWeatherHourPostList.setValue(new ArrayList<>());
    }

    /**
     * Add an observer for this view model
     *
     * @param owner the owner of the lifecycle that controls this observer
     * @param observer the official observer of this view model
     */
    public void addWeatherHourListObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super List<WeatherHourPostInfo>> observer) {
        mWeatherHourPostList.observe(owner, observer);
    }

    /**
     * In case an error occurs in the connection in which this class depends on,
     * take appropriate actions to handle this error, depending on the nature and type of error
     *
     * @param error the instance of the volley error
     */
    private void handleError(final VolleyError error) {

    }

    /**
     * If the connection in which this class depends on works and succeeds,
     * use the data that comes through this connection to implement
     * to the 24-hour weather forecast predictions list
     *
     * @param result the JSON file and data containing the need info for the 24-hour forecast fragment
     */
    private void handleResult(final JSONObject result) {

    }

    /**
     * Official connect to an API to obtain the needed data for the 24-hour forecast fragment
     */
    public void connectGet() {

    }

}
