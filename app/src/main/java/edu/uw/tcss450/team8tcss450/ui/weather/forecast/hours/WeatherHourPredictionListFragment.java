package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherHourPredictionListBinding;

/**
 * A fragment that lists and displays the 24-hour weather forecast predictions
 */
public class WeatherHourPredictionListFragment extends Fragment {

    // the view model for this class
    private WeatherHourPredictionViewModel mViewModel;

    /**
     * Construct a new weather hour prediction fragment
     */
    public WeatherHourPredictionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherHourPredictionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_hour_prediction_list,
                container, false);
        RecyclerView recyclerView = view.findViewById(R.id.hour_prediction_list_root);;
        LinearLayoutManager recyclerManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerManager);
        recyclerView.setAdapter(
                new WeatherHourRecyclerViewAdapter(WeatherHourPredictionGenerator.getPostList())
        );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherHourPredictionListBinding binding
                = FragmentWeatherHourPredictionListBinding.bind(getView());

        mViewModel.addWeatherHourListObserver(getViewLifecycleOwner(), postList-> {
            if (!postList.isEmpty()) {
                binding.hourPredictionListRoot.setAdapter(new WeatherHourRecyclerViewAdapter(postList));
            }
        });
    }

}