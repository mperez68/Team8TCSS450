package edu.uw.tcss450.team8tcss450.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherCurrentBinding;

/**
 * The fragment for the current-weather display
 *
 * @author Brandon Kennedy
 * @version 19 May 2021
 */
public class WeatherCurrentFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_current.xml
    private FragmentWeatherCurrentBinding binding;

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
        Log.v("WeatherCurrentFragment.java","onCreateView() finished");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}