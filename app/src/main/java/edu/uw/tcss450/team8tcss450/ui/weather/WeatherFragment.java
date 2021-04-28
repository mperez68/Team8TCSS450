package edu.uw.tcss450.team8tcss450.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherBinding;

/**
 * TODO Filler Class, alter as needed.
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    public FragmentWeatherBinding binding;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater);
        return binding.getRoot();

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonTwentyFourHourForecast.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherFragmentDirections
                                .actionNavigationWeatherToWeatherHourPredictionListFragment()
                )
        );


        binding.buttonTenDayForecast.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherFragmentDirections
                                .actionNavigationWeatherToWeatherDayPredictionListFragment()
                )
        );


        binding.buttonWeatherMap.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherFragmentDirections
                                .actionNavigationWeatherToWeatherMapFragment()
                )
        );
    }
}