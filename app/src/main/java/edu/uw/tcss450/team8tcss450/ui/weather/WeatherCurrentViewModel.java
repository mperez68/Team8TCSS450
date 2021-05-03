package edu.uw.tcss450.team8tcss450.ui.weather;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

/**
 * The view model that holds information to be displayed
 * on the current weather display fragment
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherCurrentViewModel extends ViewModel {

    // The encapsulated data and statistics of the current weather display
    private MutableLiveData<WeatherCurrentInfo> mWeatherInfo;

    /**
     * Construct the view model of the current weather display
     */
    public WeatherCurrentViewModel() {
        mWeatherInfo = new MutableLiveData<WeatherCurrentInfo>();
    }

    /**
     * Add an observer for the view model
     *
     * @param owner the owner of the lifecycle that controls this observer
     * @param observer the official observer of this view model
     */
    public void addObserver(@NonNull LifecycleOwner owner,
                            @NonNull Observer<? super WeatherCurrentInfo> observer) {
        mWeatherInfo.observe(owner, observer);
    }

}
