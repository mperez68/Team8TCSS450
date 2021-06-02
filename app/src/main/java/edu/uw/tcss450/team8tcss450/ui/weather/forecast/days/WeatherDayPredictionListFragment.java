package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.TextView;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherDayPredictionListBinding;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * A fragment that lists and displays the 10-day weather forecast predictions
 *
 * @author Brandon Kennedy
 * @version 22 May 2021
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

/*
        Log.d("WeatherDayPredictionListFragment.onCreate()",
                "Zipcode from Args is " + model.getZipcode() + ".  City is " + model.getCity());
        Log.d("WeatherDayPredictionListFragment.onCreate()",
                "WeatherZipcodeViewModel=" + model.getZipcode() + ", WeatherDayPredictionViewModel=" + mViewModel.getZipcode());
        // If the saved zipcode in WeatherDayPredictionViewModel does not match the zipcode
        // in WeatherZipcodeViewModel, then connect to the OpenWeatherMap API to
        // retrieve current weather data for the zipcode in WeatherZipcodeViewModel.
        // Otherwise, display information already saved in WeatherDayPredictionViewModel
        if (!mViewModel.getZipcode().equals(model.getZipcode())) {
            if (!mViewModel.isEmpty())
                mViewModel.clearList();
            mViewModel.connectToWeatherBit(model.getZipcode());
        }
 */



        Log.v("WeatherDayPredictionListFragment.java","onCreate() finished");
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

        if (!mViewModel.getLatitude().equals(model.getLatitude()) &&
                !mViewModel.getLongitude().equals(model.getLatitude())) {
            if (!mViewModel.isEmpty())
                mViewModel.clearList();
            mViewModel.connectToWeatherBit(model.getLatitude(), model.getLongitude());
        }

        mViewModel.addWeatherDayListObserver(getViewLifecycleOwner(), postList -> {
            if (!postList.isEmpty()) {
                binding.dayPredictionListRoot.setAdapter(new WeatherDayRecyclerViewAdapter(postList));
                binding.dayPredictionListRoot.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                Log.e("WeatherHourPredictionListFragment", "Turns out post list is empty: " + postList.isEmpty());
            }
        });

    }

//    @Override
//    public void onResume() {
//        WeatherZipcodeViewModel model = new ViewModelProvider(
//                getActivity()).get(WeatherZipcodeViewModel.class);
//
///*
//        Log.d("WeatherDayPredictionListFragment.onResume()",
//                "WeatherZipcodeViewModel=" + model.getZipcode() + ", WeatherDayPredictionViewModel=" + mViewModel.getZipcode());
//
//        if (!model.getZipcode().equals(mViewModel.getZipcode())) {
//            mViewModel.clearList();
//            mViewModel.connectToWeatherBit(model.getZipcode());
//        }
// */
//
//        if (!mViewModel.getLatitude().equals(model.getLatitude()) &&
//                !mViewModel.getLongitude().equals(model.getLatitude())) {
//            if (!mViewModel.isEmpty())
//                mViewModel.clearList();
//            mViewModel.connectToWeatherBit(model.getLatitude(), model.getLongitude());
//        }
//
//        Log.v("WeatherDayPredictionListFragment.java","onResume() finished");
//        super.onResume();
//    }

}