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
 * TODO Filler Class, alter as needed.
 * A simple {@link Fragment} subclass.
 */
public class WeatherMainFragment extends Fragment {

    private FragmentWeatherMainBinding binding;

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

        binding.buttonTwentyFourHourForecast.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherHourPredictionListFragment()
                )
        );


        binding.buttonTenDayForecast.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherDayPredictionListFragment()
                )
        );


        binding.buttonWeatherMap.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherMapFragment()
                )
        );
    }
}