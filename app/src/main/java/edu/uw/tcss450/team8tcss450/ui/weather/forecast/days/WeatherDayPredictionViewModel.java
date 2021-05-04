package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours.WeatherHourPostInfo;

/**
 * The view model that holds information to be displayed
 * on the 10-day weather forecast fragment
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherDayPredictionViewModel extends AndroidViewModel {

    // The encapsulated data and statistics of the 10-day weather forecast
    private MutableLiveData<List<WeatherDayPostInfo>> mWeatherDayPostList;

    /**
     * Construct the view model of the 10-day weather forecast
     *
     * @param application the application that must be created for this view model to be applied to
     */
    public WeatherDayPredictionViewModel(@NonNull Application application) {
        super(application);
        mWeatherDayPostList = new MutableLiveData<>();
        mWeatherDayPostList.setValue(new ArrayList<>());
    }

    /**
     * Add an observer for this view model
     *
     * @param owner the owner of the lifecycle that controls this observer
     * @param observer the official observer of this view model
     */
    public void addWeatherHourListObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super List<WeatherDayPostInfo>> observer) {
        mWeatherDayPostList.observe(owner, observer);
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
     * to the 10-day weather forecast predictions list
     *
     * @param result the JSON file and data containing the need info for the 10-day forecast fragment
     */
    private void handleResult(final JSONObject result) {

    }

    /**
     * Official connect to an API to obtain the needed data for the 10-day forecast fragment
     */
    public void connectGet() {

    }

}
