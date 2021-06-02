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
 * @version 25 May 2021
 */
public class WeatherCurrentFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_current.xml
    private FragmentWeatherCurrentBinding binding;

    private WeatherCurrentViewModel mViewModel;

    /**
     * Construct a new current weather fragment
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

//        Log.d("WeatherCurrentFragment.onCreateView()",
//                "WeatherZipcodeViewModel=" + model.getZipcode() + ", WeatherCurrentViewModel=" + mViewModel.getZipcode());

        // If the saved zipcode in WeatherCurrentViewModel does not match the zipcode
        // in WeatherZipcodeViewModel, then connect to the OpenWeatherMap API to
        // retrieve current weather data for the zipcode in WeatherZipcodeViewModel.
        // Otherwise, display information already saved in WeatherCurrentViewModel
        //if (!mViewModel.getZipcode().equals(model.getZipcode())) {
            //mViewModel.connectToOpenWeatherMap(model.getZipcode(), model, binding);
        if (!mViewModel.getLatitude().equals(model.getLatitude()) &&
                !mViewModel.getLongitude().equals(model.getLongitude())) {
            mViewModel.connectToOpenWeatherMap(model.getLatitude(), model.getLongitude(), binding);
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

//        Log.d("WeatherCurrentFragment.onResume()",
//                "WeatherZipcodeViewModel=" + model.getZipcode() + ", WeatherCurrentViewModel=" + mViewModel.getZipcode());
//        if (!model.getZipcode().equals(mViewModel.getZipcode())) {
//            mViewModel.connectToOpenWeatherMap(model.getZipcode(), model, binding);
        if (!model.getLatitude().equals(mViewModel.getLatitude()) &&
                !model.getLongitude().equals(mViewModel.getLongitude())) {
            mViewModel.connectToOpenWeatherMap(model.getLatitude(), model.getLongitude(), binding);
        } else {
            mViewModel.displayInformation(binding, mViewModel.getWeatherInfo());
        }

        Log.v("WeatherCurrentFragment.java","onResume() finished");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Attempt to connect to the OpenWeatherMap to get the needed
     * information for queried zipcode
     *
     * @param zipcode the queried zipcode
     * @param model the WeatherZipcodeViewModel to be used in case queried zipcode is valid
    public void getInformation(final String zipcode, final WeatherZipcodeViewModel model) {
        mViewModel.connectToOpenWeatherMap(zipcode, model, binding);
    }
     */

}