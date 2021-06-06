package edu.uw.tcss450.team8tcss450.ui.weather.current;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherCurrentBinding;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;
import edu.uw.tcss450.team8tcss450.ui.weather.forecast.days.WeatherDayPredictionViewModel;
import edu.uw.tcss450.team8tcss450.ui.weather.forecast.days.WeatherDayRecyclerViewAdapter;
import edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours.WeatherHourPredictionViewModel;
import edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours.WeatherHourRecyclerViewAdapter;

/**
 * The fragment for the current-weather display
 *
 * @author Brandon Kennedy
 * @version 2 June 2021
 */
public class WeatherCurrentFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_current.xml
    private FragmentWeatherCurrentBinding mBinding;

    // The view model for the weather current fragment
    private WeatherCurrentViewModel mCurrentViewModel;

    private WeatherZipcodeViewModel mZipcodeViewModel;

    private WeatherDayPredictionViewModel mDayViewModel;

    private WeatherHourPredictionViewModel mHourViewModel;

    /**
     * Construct a new weather current fragment
     */
    public WeatherCurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentWeatherCurrentBinding.inflate(inflater);

        mCurrentViewModel = new ViewModelProvider(getActivity())
                .get(WeatherCurrentViewModel.class);

        mZipcodeViewModel = new ViewModelProvider(getActivity())
                .get(WeatherZipcodeViewModel.class);

        mDayViewModel = new ViewModelProvider(getActivity())
                .get(WeatherDayPredictionViewModel.class);

        mHourViewModel = new ViewModelProvider(getActivity())
                .get(WeatherHourPredictionViewModel.class);

        // If the saved lat/long in WeatherCurrentViewModel does not match the lat/long
        // in WeatherZipcodeViewModel, then connect to the OpenWeatherMap API to
        // retrieve current weather data for the lat/long in WeatherZipcodeViewModel.
        // Otherwise, display information already saved in WeatherCurrentViewModel
        if (!mCurrentViewModel.getLatitude().equals(mZipcodeViewModel.getLatitude()) ||
                !mCurrentViewModel.getLongitude().equals(mZipcodeViewModel.getLongitude())) {
            mCurrentViewModel.connectToOpenWeatherMap(mZipcodeViewModel.getLatitude(), mZipcodeViewModel.getLongitude(), mBinding, mZipcodeViewModel);
            mDayViewModel.clearList();
            mDayViewModel.connectToWeatherBit(mZipcodeViewModel.getLatitude(), mZipcodeViewModel.getLongitude(), mZipcodeViewModel);
        } else {
            mCurrentViewModel.displayInformation(mBinding, mCurrentViewModel.getWeatherInfo());
        }

        mDayViewModel.addWeatherDayListObserver(getViewLifecycleOwner(), postList -> {
            if (!postList.isEmpty()) {
                mBinding.dayPredictionListRoot.setAdapter(new WeatherDayRecyclerViewAdapter(postList));
                mBinding.dayPredictionListRoot.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        Log.v("WeatherCurrentFragment.java","onCreateView() finished");
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!mHourViewModel.getLatitude().equals(mZipcodeViewModel.getLatitude()) &&
                !mHourViewModel.getLongitude().equals(mZipcodeViewModel.getLongitude())) {
            mHourViewModel.connectToOpenWeatherMap(mZipcodeViewModel.getLatitude(), mZipcodeViewModel.getLongitude(), mZipcodeViewModel);
        } else {
            mCurrentViewModel.displayInformation(mBinding, mCurrentViewModel.getWeatherInfo());
        }

        mHourViewModel.addWeatherHourListObserver(getViewLifecycleOwner(), postList -> {
            if (!postList.isEmpty()) {
                mBinding.hourPredictionListRoot.setAdapter(new WeatherHourRecyclerViewAdapter(postList));
                mBinding.hourPredictionListRoot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            }
        });
    }
}