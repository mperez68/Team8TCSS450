package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherHourPredictionListBinding;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * A fragment that lists and displays the 24-hour weather forecast predictions
 *
 * @author Brandon Kennedy
 * @version 19 May 2021
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

        WeatherHourPredictionListFragmentArgs args =
                WeatherHourPredictionListFragmentArgs.fromBundle(getArguments());

        Log.i("WeatherHourPredictionListFragment.onCreate()",
                "Zipcode from Args is " + args.getZipcode() + ".  City is " + args.getCity());

        if (!args.getZipcode().equals("") && !args.getCity().equals(""))
            mViewModel.connectToWeatherBit(args.getZipcode());
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
        WeatherZipcodeViewModel model =
                new ViewModelProvider(getActivity()).get(WeatherZipcodeViewModel.class);

        if (!model.getZipcode().equals(""))
            binding.hourPredictionListCity.setText(model.getCity());

        mViewModel.addWeatherHourListObserver(
            getViewLifecycleOwner(),
            postList -> {
                if (!postList.isEmpty()) {
                    binding.hourPredictionListRoot.setAdapter(new WeatherHourRecyclerViewAdapter(postList));
                    binding.hourPredictionListRoot.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        );

        mViewModel.addResponseObserver(
            getViewLifecycleOwner(),
            this::observeWeatherBitResponse
        );
    }


    @Override
    public void onDestroy() {
        mViewModel.clearList();
        super.onDestroy();
    }


    /**
     * Get the JSONObject as a response from the WeatherBit API
     * and retrieve the vital latitude/longitude coordinates
     * to be used as parameters for the connection to the OpenWeatherMap API.
     *
     * @param response the JSONObject retrieved from the WeatherBit API
     */
    private void observeWeatherBitResponse(final JSONObject response) {
        Log.i("WeatherHourPredictionListFragment.java", "We now observe response to JSON Object retrieved from WeatherBit API");
        Log.i("WeatherHourPredictionListFragment.java", response.toString());
        if (response.length() > 0) {
            if (response.has("data")) {
                try {
                    JSONObject data = response.getJSONArray("data").getJSONObject(0);
                    String lat = String.valueOf(data.getDouble("lat"));
                    String lon = String.valueOf(data.getDouble("lon"));
                    Log.i("WeatherHourPredictionListFragment.java","lat=" + lat + ", lon=" + lon);

                    mViewModel.connectToOpenWeatherMap(lat, lon);
                } catch (JSONException e) {
                    Log.e("JSON Response", "Error in getting lat/long coordinates");
                }
            } else {
                Log.e("JSON Response", "Response does not contain data");
            }
        } else {
            Log.d("JSON Response in WeatherHourPredictionListFragment.java", "No Response");
        }
    }

}