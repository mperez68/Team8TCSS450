package edu.uw.tcss450.team8tcss450.ui.weather.map;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * The view model for the weather map fragment
 *
 * @author Brandon Kennedy
 * @version 30 May 2021
 */
public class WeatherMapViewModel extends AndroidViewModel {

    private MutableLiveData<Location> mLocation;

    /**
     * Constructor for the weather map view model
     *
     * @param application the application that must be created for this view model to be applied to
     */
    public WeatherMapViewModel(@NonNull Application application) {
        super(application);
        mLocation = new MediatorLiveData<>();
    }

    /**
     * Add an observer that handles the response to get a
     * new location from the Google map
     *
     * @param owner the owner of the lifecycle that controls this observer
     * @param observer the official observer of this view model
     */
    public void addLocationObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super Location> observer) {
        mLocation.observe(owner, observer);
    }

    /**
     * Set the location coordinates for the Google map
     *
     * @param location the location on the Google map
     */
    public void setLocation(final Location location) {
        if (!location.equals(mLocation.getValue())) {
            mLocation.setValue(location);
        }
    }

    /**
     * Get the location coordinates
     *
     * @return the location coordinates
     */
    public Location getCurrentLocation() {
        return new Location(mLocation.getValue());
    }
}
