package edu.uw.tcss450.team8tcss450.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherCurrentBinding;

/**
 * The fragment for the current-weather display
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherCurrentFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_current.xml
    private FragmentWeatherCurrentBinding binding;

    // The view model for this class.
    private WeatherCurrentViewModel mWeatherCurrentViewModel;

    /**
     * Construct a new current weather fragement
     */
    public WeatherCurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherCurrentViewModel = new ViewModelProvider(getActivity())
                .get(WeatherCurrentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherCurrentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}