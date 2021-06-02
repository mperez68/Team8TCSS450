package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherHourPredictionListBinding;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * A fragment that lists and displays the 24-hour weather forecast predictions
 *
 * @author Brandon Kennedy
 * @version 25 May 2021
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

        mViewModel = new ViewModelProvider(
                getActivity()).get(WeatherHourPredictionViewModel.class);

        WeatherZipcodeViewModel model =
                new ViewModelProvider(getActivity()).get(WeatherZipcodeViewModel.class);

//        Log.d("WeatherHourPredictionListFragment.onCreate()",
//                "Zipcode from Args is " + model.getZipcode() + ".  City is " + model.getCity());
//        Log.d("WeatherHourPredictionListFragment.onCreate()",
//                "WeatherZipcodeViewModel=" + model.getZipcode() + ", WeatherHourPredictionViewModel=" + mViewModel.getZipcode());
        // If the saved zipcode in WeatherHourPredictionViewModel does not match the zipcode
        // in WeatherZipcodeViewModel, then connect to the OpenWeatherMap API to
        // retrieve current weather data for the zipcode in WeatherZipcodeViewModel.
        // Otherwise, display information already saved in WeatherHourPredictionViewModel
        /*
        if (!mViewModel.getZipcode().equals(model.getZipcode())) {
            if (!mViewModel.isEmpty())
                mViewModel.clearList();
            mViewModel.connectToWeatherBit(model.getZipcode());
        }
        */
        if (!mViewModel.getLatitude().equals(model.getLatitude()) &&
                !mViewModel.getLongitude().equals(model.getLatitude())) {
            if (!mViewModel.isEmpty())
                mViewModel.clearList();
            mViewModel.connectToOpenWeatherMap(model.getLatitude(), model.getLongitude());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_hour_prediction_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherHourPredictionListBinding binding
            = FragmentWeatherHourPredictionListBinding.bind(getView());

        mViewModel.addWeatherHourListObserver(
            getViewLifecycleOwner(),
            postList -> {
                if (!postList.isEmpty()) {
                    binding.hourPredictionListRoot.setAdapter(new WeatherHourRecyclerViewAdapter(postList));
                    binding.hourPredictionListRoot.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        );
    }

    @Override
    public void onResume() {
        WeatherZipcodeViewModel model = new ViewModelProvider(
                getActivity()).get(WeatherZipcodeViewModel.class);

        //Log.d("WeatherHourPredictionListFragment.onResume()",
        //        "WeatherZipcodeViewModel=" + model.getZipcode() + ", WeatherHourPredictionViewModel=" + mViewModel.getZipcode());
/*
        if (!model.getZipcode().equals(mViewModel.getZipcode())) {
            mViewModel.clearList();
            mViewModel.connectToWeatherBit(model.getZipcode());
        }
 */

        if (!mViewModel.getLatitude().equals(model.getLatitude()) &&
                !mViewModel.getLongitude().equals(model.getLatitude())) {
            if (!mViewModel.isEmpty())
                mViewModel.clearList();
            mViewModel.connectToOpenWeatherMap(model.getLatitude(), model.getLongitude());
        }

        Log.v("WeatherHourPredictionListFragment.java","onResume() finished");
        super.onResume();
    }

}