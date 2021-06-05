package edu.uw.tcss450.team8tcss450.ui.weather.current;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherCurrentBinding;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * The fragment for the current-weather display
 *
 * @author Brandon Kennedy
 * @version 2 June 2021
 */
public class WeatherCurrentFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_current.xml
    private FragmentWeatherCurrentBinding binding;

    // The view model for the weather current fragment
    private WeatherCurrentViewModel mViewModel;

    /**
     * Construct a new weather current fragment
     */
    public WeatherCurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherCurrentBinding.inflate(inflater);

        WeatherZipcodeViewModel model = new ViewModelProvider(
                getActivity()).get(WeatherZipcodeViewModel.class);

        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherCurrentViewModel.class);

        // If the saved lat/long in WeatherCurrentViewModel does not match the lat/long
        // in WeatherZipcodeViewModel, then connect to the OpenWeatherMap API to
        // retrieve current weather data for the lat/long in WeatherZipcodeViewModel.
        // Otherwise, display information already saved in WeatherCurrentViewModel
        if (!mViewModel.getLatitude().equals(model.getLatitude()) ||
                !mViewModel.getLongitude().equals(model.getLongitude())) {
            mViewModel.connectToOpenWeatherMap(model.getLatitude(), model.getLongitude(), binding, model);
        } else {
            mViewModel.displayInformation(binding, mViewModel.getWeatherInfo());
        }

        Log.v("WeatherCurrentFragment.java","onCreateView() finished");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        WeatherZipcodeViewModel model = new ViewModelProvider(
                getActivity()).get(WeatherZipcodeViewModel.class);

        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherCurrentViewModel.class);

        if (!model.getLatitude().equals(mViewModel.getLatitude()) &&
                !model.getLongitude().equals(mViewModel.getLongitude())) {
            mViewModel.connectToOpenWeatherMap(model.getLatitude(), model.getLongitude(), binding, model);
        } else {
            mViewModel.displayInformation(binding, mViewModel.getWeatherInfo());
        }

        Log.v("WeatherCurrentFragment.java","onResume() finished");
        super.onResume();
    }


}