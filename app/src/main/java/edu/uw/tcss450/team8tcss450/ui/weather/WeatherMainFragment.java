package edu.uw.tcss450.team8tcss450.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherMainBinding;

/**
 * The main page for the weather feature.
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherMainFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_main.xml
    private FragmentWeatherMainBinding binding;

    /**
     * Construct the main weather fragment
     */
    public WeatherMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherMainBinding.inflate(inflater);
        return binding.getRoot();

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the click listener for the 24-Hour Forecast button.
        binding.buttonTwentyFourHourForecast.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherHourPredictionListFragment()
                )
        );

        // Set the click listener for the 10-Day Forecast button.
        binding.buttonTenDayForecast.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherDayPredictionListFragment()
                )
        );

        // Set the click listener for the Weather Map button.
        binding.buttonWeatherViewMap.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherMapFragment()
                )
        );
    }
}