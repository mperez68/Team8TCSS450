package edu.uw.tcss450.team8tcss450.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherMapBinding;

/**
 * The page for the weather map
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherMapFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_map.xml
    public FragmentWeatherMapBinding binding;

    /**
     * Constructor for the weather map fragment
     */
    public WeatherMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherMapBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}