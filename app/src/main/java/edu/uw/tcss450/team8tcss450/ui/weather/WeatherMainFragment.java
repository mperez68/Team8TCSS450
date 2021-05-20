package edu.uw.tcss450.team8tcss450.ui.weather;

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

import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentWeatherMainBinding;

/**
 * The main page for the weather feature.
 *
 * @author Brandon Kennedy
 * @version 19 May 2021
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

        new ViewModelProvider(
            getActivity(),
            new WeatherZipcodeViewModel.WeatherZipcodeViewModelFactory("", ""))
                .get(WeatherZipcodeViewModel.class);

        WeatherZipcodeViewModel model = new ViewModelProvider(
                getActivity()).get(WeatherZipcodeViewModel.class);

        mWeatherMainModel = new ViewModelProvider(getActivity())
                .get(WeatherMainViewModel.class);
        mWeatherMainModel.setZipcode(model.getZipcode());

        if (!model.getCity().equals("") && !model.getZipcode().equals(""))
            mWeatherMainModel.connect(model.getZipcode());

        Log.v("WeatherMainFragment.java","onCreate() finished");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherMainBinding.inflate(inflater);
        Log.v("WeatherMainFragment.java","onCreateView() finished");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the click listener for the search button.
        binding.buttonSearchZipcode.setOnClickListener(this::getWeatherForZipcode);

        // Set the click listener for the 24-Hour Forecast button.
        binding.buttonTwentyFourHourForecast.setOnClickListener(button -> {
                WeatherZipcodeViewModel model =
                        new ViewModelProvider(getActivity()).get(WeatherZipcodeViewModel.class);

                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherHourPredictionListFragment(
                                        model.getZipcode(), model.getCity()
                        )
                );
        });

        // Set the click listener for the 10-Day Forecast button.
        binding.buttonTenDayForecast.setOnClickListener(button -> {
                WeatherZipcodeViewModel model =
                        new ViewModelProvider(getActivity()).get(WeatherZipcodeViewModel.class);

                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherDayPredictionListFragment(
                                        model.getZipcode(), model.getCity()
                        )
                );
        });

        // Set the click listener for the Weather Map button.
        binding.buttonWeatherViewMap.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherMapFragment()
                )
        );

        mWeatherMainModel.addObserver(
                getViewLifecycleOwner(),
                this::observeResponse
        );

        Log.v("WeatherMainFragment.java","onViewCreated() finished");
    }

    @Override
    public void onResume() {
        WeatherZipcodeViewModel model = new ViewModelProvider(
                getActivity()).get(WeatherZipcodeViewModel.class);

        mWeatherMainModel = new ViewModelProvider(getActivity())
                .get(WeatherMainViewModel.class);
        mWeatherMainModel.setZipcode(model.getZipcode());


        if (!model.getCity().equals("") && !model.getZipcode().equals(""))
            mWeatherMainModel.connect(model.getZipcode());

        Log.v("WeatherMainFragment.java","onResume() finished");
        super.onResume();
    }

    /**
     * Observer the response JSON object from
     * attempt to connect to the OpenWeatherMap API
     *
     * @param response the JSON object being observed for validity
     */
    private void observeResponse(final JSONObject response) {
        WeatherZipcodeViewModel model =
                new ViewModelProvider(getActivity()).get(WeatherZipcodeViewModel.class);
        IntFunction<String> getString = getResources()::getString;

        if (!mWeatherMainModel.getZipcode().equals("")) {
            if (response.length() > 0 && !response.has("error")) {
                Log.i("WeatherMainFragment.java", "We have a JSON. Now we open it to look for 'cod' key");
                if (response.has("cod")) {
                    Log.i("WeatherMainFragment.java", "JSON object has 'cod' key, 'cod': " + String.valueOf(response.optInt("cod", 400)));
                    try {
                        if (response.optInt("cod", 400) != 200 ) {
                            Log.e("WeatherMainFragment.java", "Looks like cod is not equal to 200. This JSON file has no data.");
                            binding.zipcodeSearchField.setError(
                                    "Error Authenticating: " +
                                            response.getString("message"));
                        } else {
                            Log.i("JSON Response", "JSON response object has no error code key");

                            String city = response.getString("name");

                            Log.d("WeatherMainFragment.observeResponse()",
                                    "WeatherZipcodeViewModel before being set. Zipcode:"
                                            + model.getZipcode() + ", City:" + model.getCity());

                            model.setZipcode(mWeatherMainModel.getZipcode());
                            model.setCity(city);

                            Log.d("WeatherMainFragment.observeResponse()",
                                    "WeatherZipcodeViewModel after being set. Zipcode:"
                                            + model.getZipcode() + ", City:" + model.getCity());

                            binding.zipcodeQueriedLocation.setVisibility(View.VISIBLE);
                            binding.weatherCurrentFragment.getRoot().setVisibility(View.VISIBLE);
                            binding.zipcodeQueriedLocation.setText(city);

                            binding.buttonTwentyFourHourForecast.setClickable(true);

                            String readTemp = String.valueOf(
                                    response.getJSONObject(getString(R.string.jsonkey_weathermain_main))
                                            .getInt(getString(R.string.jsonkey_weathermain_temp)));
                            binding.weatherCurrentFragment.temperatureReading.setText(readTemp + "\u00B0F");

                            String readOutlook = String.valueOf(
                                    response.getJSONArray(getString(R.string.jsonkey_weathermain_weather))
                                            .getJSONObject(0)
                                            .getString(getString(R.string.jsonkey_weathermain_main)));
                            binding.weatherCurrentFragment.outlookReading.setText(readOutlook);

                            String readHumidity = String.valueOf(
                                    response.getJSONObject(getString(R.string.jsonkey_weathermain_main))
                                            .getInt(getString(R.string.jsonkey_weathermain_humidity))) + "%";
                            binding.weatherCurrentFragment.humidityReading.setText(readHumidity);

                            String windSpeed = String.valueOf(
                                    response.getJSONObject(getString(R.string.jsonkey_weathermain_wind))
                                            .getInt(getString(R.string.jsonkey_weathermain_speed)));
                            int windDegree =
                                    response.getJSONObject(getString(R.string.jsonkey_weathermain_wind))
                                            .getInt(getString(R.string.jsonkey_weathermain_deg));
                            binding.weatherCurrentFragment.windReading
                                    .setText(windDirection(windDegree) + " " + windSpeed + " MPH");

                        }
                    } catch (JSONException e) {
                        Log.e("JSON Response Parse Error", e.getMessage());
                    }
                } else {
                    Log.d("JSON Response", "JSON Object does not contain 'cod' key");
                    binding.zipcodeSearchField.setError("Invalid Entry. Please enter a valid zipcode.");
                }
            } else {
                Log.d("JSON Response", "JSON Object contains 'error' key");
                binding.zipcodeSearchField.setError("Invalid Entry. Please enter a valid zipcode.");
            }
        }

        Log.i("WeatherMainFragment.java", "We now observe response to JSON Object retrieved from OpenWeatherMap API");
        Log.i("WeatherMainFragment.java", response.toString());

    }

    /**
     * Connect to the OpenWeatherAPI when the button using this method is clicked
     *
     * @param view the view required to set this method to a button
     */
    private void getWeatherForZipcode(View view) {
        String zipcode = binding.zipcodeSearchField.getText().toString();
        if (zipcode.equals("")) {
            Log.e("WeatherMainFragment.getWeatherForZipcode()", "Nothing was entered for zipcode.");
            binding.zipcodeSearchField.setError("No entry. Please enter a valid zipcode.");
        } else {
            mWeatherMainModel.setZipcode(zipcode);
            mWeatherMainModel.connect(zipcode);
        }

    }

    /**
     * Get the wind direction label depending on degrees
     * relative to a clockwise compass (0 degrees for North, 180 degrees for South)
     *
     * @param degrees the degree amount (0 to 360)
     * @return the compass direction of the wind
     */
    private String windDirection(final int degrees) {
        String[] directions = {
                "N", "NNE", "NE", "ENE",
                "E", "ESE", "SE", "SSE",
                "S", "SSW", "SW", "WSW",
                "W", "WNW", "NW", "NNW"  };
        if (degrees >= 0 && degrees <= 360) {
            return directions[(int) (Math.floor(degrees/22.5) % directions.length)];
        } else {
            return "NaN";
        }
    }

}