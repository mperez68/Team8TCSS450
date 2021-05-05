package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherDayPredictionListBinding;

/**
 * A fragment that lists and displays the 10-day weather forecast predictions
 */
public class WeatherDayPredictionListFragment extends Fragment {

    // the view model for this class
    private WeatherDayPredictionViewModel mViewModel;

    /**
     * Construct a new weather day prediction fragment
     */
    public WeatherDayPredictionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherDayPredictionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_day_prediction_list,
                container, false);
        RecyclerView recyclerView = view.findViewById(R.id.day_prediction_list_root);
        LinearLayoutManager recyclerManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerManager);
        recyclerView.setAdapter(
                new WeatherDayRecyclerViewAdapter(WeatherDayPredictionGenerator.getWeatherInfoList())
        );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherDayPredictionListBinding binding
                = FragmentWeatherDayPredictionListBinding.bind(getView());
        mViewModel.addWeatherHourListObserver(getViewLifecycleOwner(), postList -> {
            if (!postList.isEmpty()) {
                binding.dayPredictionListRoot.setAdapter(new WeatherDayRecyclerViewAdapter(postList));
            }
        });
    }

}