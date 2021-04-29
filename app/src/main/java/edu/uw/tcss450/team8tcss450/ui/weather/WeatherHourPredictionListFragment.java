package edu.uw.tcss450.team8tcss450.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherBinding;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherHourPredictionListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherHourPredictionListFragment extends Fragment {

    public FragmentWeatherHourPredictionListBinding binding;

    public WeatherHourPredictionListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherHourPredictionListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}