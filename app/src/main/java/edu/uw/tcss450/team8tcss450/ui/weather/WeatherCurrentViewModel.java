package edu.uw.tcss450.team8tcss450.ui.weather;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class WeatherCurrentViewModel extends ViewModel {

    private MutableLiveData<WeatherCurrentInfo> mWeatherInfo;

    public WeatherCurrentViewModel() {
        mWeatherInfo = new MutableLiveData<WeatherCurrentInfo>();
    }

    public void addObserver(@NonNull LifecycleOwner owner,
                            @NonNull Observer<? super WeatherCurrentInfo> observer) {
        mWeatherInfo.observe(owner, observer);
    }

}
