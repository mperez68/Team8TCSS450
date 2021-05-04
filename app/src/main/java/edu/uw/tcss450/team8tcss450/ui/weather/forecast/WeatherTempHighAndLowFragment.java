package edu.uw.tcss450.team8tcss450.ui.weather.forecast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherTempHighAndLowBinding;

/**
 * The high-and-low temperature component for the forecast list
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherTempHighAndLowFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_temp_high_and_low.xml
    public FragmentWeatherTempHighAndLowBinding binding;

    /**
     * Constructor for the high and low weather temperature
     */
    public WeatherTempHighAndLowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherTempHighAndLowBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}