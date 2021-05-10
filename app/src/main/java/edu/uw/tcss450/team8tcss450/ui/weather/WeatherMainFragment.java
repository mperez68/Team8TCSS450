package edu.uw.tcss450.team8tcss450.ui.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherMainBinding;

/**
 * The main page for the weather feature.
 *
 * @author Brandon Kennedy
 * @version 10 May 2021
 */
public class WeatherMainFragment extends Fragment {

    // The binding of this class to the XML view fragment_weather_main.xml
    private FragmentWeatherMainBinding binding;

    // The view model associated with this Weather Main Fragment
    private WeatherMainViewModel mWeatherMainModel;

    /**
     * Construct the main weather fragment
     */
    public WeatherMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherMainModel = new ViewModelProvider(getActivity())
                .get(WeatherMainViewModel.class);
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

        // Set the click listener for the search button.
        binding.buttonSearchZipcode.setOnClickListener(this::getWeatherForZipcode);


        // Set the click listener for the 24-Hour Forecast button.
        binding.buttonTwentyFourHourForecast.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherHourPredictionListFragment()
                )
        );

        // Set the click listener for the 10-Day Forecast button.
        binding.buttonTenDayForecast.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherDayPredictionListFragment()
                )
        );

        // Set the click listener for the Weather Map button.
        binding.buttonWeatherViewMap.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherMapFragment()
                )
        );

        mWeatherMainModel.addObserver(
                getViewLifecycleOwner(),
                this::observeResponse);
    }

    /**
     * Observer the response JSON object from
     * attempt to connect to the OpenWeatherMap API
     *
     * @param response the JSON object being observed for validity
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.zipcodeSearchField.setError("Error Authenticating: " +
                            response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                try {
                    Log.i("JSON Response", "JSON response object has no code key");
                    Log.i("JSON Response", "Zipcode Coordinates: " + response.getString("coord"));
                    binding.zipcodeQuery.setText(response.getString("name"));
                } catch (JSONException e) {
                    Log.e("JSON Response Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    /**
     * Connect to the OpenWeatherAPI when the button using this method is clicked
     *
     * @param view the view required to set this method to a button
     */
    public void getWeatherForZipcode(View view) {
        String zipcode = binding.zipcodeSearchField.getText().toString();
        mWeatherMainModel.connect(zipcode);
    }

}