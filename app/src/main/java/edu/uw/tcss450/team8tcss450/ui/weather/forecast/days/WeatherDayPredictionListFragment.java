package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherDayPredictionListBinding;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * A fragment that lists and displays the 10-day weather forecast predictions
 *
 * @author Brandon Kennedy
 * @version 19 May 2021
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

        WeatherDayPredictionListFragmentArgs args =
                WeatherDayPredictionListFragmentArgs.fromBundle(getArguments());

        Log.i("WeatherDayPredictionListFragment.onCreate()",
                "Zipcode from Args is " + args.getZipcode() + ".  City is " + args.getCity());
        if (!args.getZipcode().equals("") && !args.getCity().equals(""))
            mViewModel.connect(args.getZipcode());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_day_prediction_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherDayPredictionListBinding binding
                = FragmentWeatherDayPredictionListBinding.bind(getView());

        WeatherZipcodeViewModel model = new ViewModelProvider(getActivity())
                .get(WeatherZipcodeViewModel.class);

        if (!model.getZipcode().equals(""))
            binding.dayPredictionListCity.setText(model.getCity());

        mViewModel.addWeatherDayListObserver(getViewLifecycleOwner(), postList -> {
            if (!postList.isEmpty()) {
                binding.dayPredictionListRoot.setAdapter(new WeatherDayRecyclerViewAdapter(postList));
                binding.dayPredictionListRoot.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }

    @Override
    public void onDestroy() {
        mViewModel.clearList();
        super.onDestroy();
    }

}