package edu.uw.tcss450.team8tcss450.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.weather.current.WeatherCurrentFragment;
import edu.uw.tcss450.team8tcss450.ui.weather.forecast.days.WeatherDayPredictionListFragment;
import edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours.WeatherHourPredictionListFragment;
import edu.uw.tcss450.team8tcss450.ui.weather.map.WeatherMapFragment;

/**
 * The primary page for the weather fragment that is
 * the parent fragment for the child tab weather fragments.
 *
 * @author Brandon Kennedy
 * @version 31 May 2021
 */
public class WeatherMainFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private WeatherPageAdapter mPagerAdapter;

    //private WeatherCurrentFragment currentFragment;
    //private WeatherHourPredictionListFragment hourPredictionListFragment;
    //private WeatherDayPredictionListFragment dayPredictionListFragment;
    //private WeatherMapFragment mapFragment;

    public WeatherMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        new ViewModelProvider(
            getActivity(),
            new WeatherZipcodeViewModel.WeatherZipcodeViewModelFactory(
                "Tacoma","47.2643","-122.4575"))
                .get(WeatherZipcodeViewModel.class);
        */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_weather_main, container, false);


        //mPagerAdapter.addFragment(new WeatherMapFragment());

        //mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        /*
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                mPagerAdapter.notifyDataSetChanged();
                if (tab.getPosition() == 0) {
                    Log.i("WeatherPrimaryFragment", "Selected Tab " + tab.getPosition() + " is Current Weather Tab");
                    mPagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    Log.i("WeatherPrimaryFragment", "Selected Tab " + tab.getPosition() + " is 24-Hour Forecast Tab");
                    mPagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    Log.i("WeatherPrimaryFragment", "Selected Tab " + tab.getPosition() + " is 10-Day Forecast Tab");
                    mPagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 3) {
                    Log.i("WeatherPrimaryFragment", "Selected Tab " + tab.getPosition() + " is Weather Map Tab");
                    mPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
         */



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPagerAdapter = new WeatherPageAdapter(
                getChildFragmentManager()
        );
        mViewPager = view.findViewById(R.id.weather_view_pager);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout = view.findViewById(R.id.weather_tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("Current Weather"));
        //mPagerAdapter.addFragment(new WeatherCurrentFragment());

        mTabLayout.addTab(mTabLayout.newTab().setText("24-Hour Forecast"));
        //mPagerAdapter.addFragment(new WeatherHourPredictionListFragment());

        mTabLayout.addTab(mTabLayout.newTab().setText("10-Day Forecast"));
        //mPagerAdapter.addFragment(new WeatherDayPredictionListFragment());

        mTabLayout.addTab(mTabLayout.newTab().setText("Weather Map"));

        WeatherZipcodeViewModel model = new ViewModelProvider(
                getActivity()).get(WeatherZipcodeViewModel.class);

        Button searchZipcode = view.findViewById(R.id.button_search_zipcode);
        searchZipcode.setOnClickListener(v -> {
            TextView zipcodeField = view.findViewById(R.id.zipcode_search_field);
            Log.i("Search Button Pushed", "Zipcode query is " + zipcodeField.getText().toString());
            this.connectToOpenWeatherMap(zipcodeField.getText().toString(), model, view);
        });

        TextView city = view.findViewById(R.id.zipcode_queried_location);
        city.setText(model.getCity());
    }

    /**
     * If connection to the OpenWeatherMap API is successful, a JSONObject
     * is retrieved from it and is handled for validation.  If JSONObject
     * is valid, we refresh the currently selected tab.
     *
     * @param response the JSONObject retrieved from OpenWeatherMap API
     * @param zipcode the zipcode in which the JSONObject was retrieved for
     * @param model the WeatherZipcodeViewModel in which if JSONObject is valid, the zipcode and city will be stored there
     */
    private void handleResultFromOpenWeatherMap(final JSONObject response,
                                                final String zipcode,
                                                WeatherZipcodeViewModel model,
                                                final View view) {
        if (validateRetrievedJSONObject(response)) {
            try {
                String city = response.getString("name");
                String latitude = String.valueOf(response.getJSONObject("coord").getDouble("lat"));
                String longitude = String.valueOf(response.getJSONObject("coord").getDouble("lon"));
                Log.i("WeatherPrimaryFragment.java", "Zipcode from search query is valid");
                Log.i("WeatherPrimaryFragment.java",
                        "Zipcode= " + zipcode + ", City= " + city + ", " +
                        "Latitude/Longitude=" + latitude + "," + longitude);

                // Set the new WeatherZipcodeViewModel with the newest validated zipcode and city local
                // Thus, display the new city name on WeatherMainFragment
                //model.setZipcode(zipcode);
                model.setCity(city);
                model.setLocation(latitude, longitude);
                TextView cityText = view.findViewById(R.id.zipcode_queried_location);
                cityText.setText(model.getCity());

                //Log.d("WeatherMainFragment", "WeatherZipcodeViewModel now has " + model.getZipcode() + " and " + model.getCity());

                // Refresh the currently selected tab with the new weather data
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.setReorderingAllowed(true);
                if (mViewPager.getCurrentItem() == 0) {
                    Log.d("WeatherMainFragment",
                            "Current Item is " + mViewPager.getCurrentItem() +
                                    ", so we should be refreshing CurrentWeatherCurrentFragment");
                    ft.detach(mPagerAdapter.getItem(0)).attach(new WeatherMapFragment());
                    //ft.replace(R.id.weather_view_pager, WeatherCurrentFragment.class, null);
                } else if (mViewPager.getCurrentItem() == 1) {
                    Log.d("WeatherMainFragment",
                            "Current Item is " + mViewPager.getCurrentItem() +
                                    ", so we should be refreshing WeatherHourPredictionListFragment");
                    ft.detach(mPagerAdapter.getItem(1)).attach(new WeatherHourPredictionListFragment());
                    //ft.replace(R.id.weather_view_pager, WeatherHourPredictionListFragment.class, null);
                } else if (mViewPager.getCurrentItem() == 2) {
                    Log.d("WeatherMainFragment",
                            "Current Item is " + mViewPager.getCurrentItem() +
                                    ", so we should be refreshing WeatherDayPredictionListFragment");
                    ft.detach(mPagerAdapter.getItem(2)).attach(new WeatherDayPredictionListFragment());
                    //ft.replace(R.id.weather_view_pager, WeatherDayPredictionListFragment.class, null);
                } else if (mViewPager.getCurrentItem() == 3) {
                    Log.d("WeatherMainFragment",
                            "Current Item is " + mViewPager.getCurrentItem() +
                                    ", so we should be refreshing WeatherMapFragment");
                    ft.detach(mPagerAdapter.getItem(3)).attach(new WeatherMapFragment());
                }
                ft.addToBackStack(null);
                ft.commit();
                //getActivity().recreate();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("ERROR!", e.getMessage());
            }
        } else {
            EditText zipcodeField = view.findViewById(R.id.zipcode_search_field);
            zipcodeField.setError("Invalid Entry. Please enter another zipcode.");
            Log.e("WeatherPrimaryFragment.java", "Zipcode from search query isn't valid");
        }
    }

    /**
     * Validate the JSONObject retrieved from OpenWeatherMap API
     * and return if the JSONObject is valid
     *
     * @param response the JSONObject retrieved from OpenWeatherMap API
     * @return the boolean that tells if the retrieved JSONObject tells that the queried zipcode is valid or not
     */
    private Boolean validateRetrievedJSONObject(final JSONObject response) {
        if (response.length() > 0 && !response.has("error")) {
            Log.i("WeatherMainFragment.java", "We have a JSON. Now we open it to look for 'cod' key");
            if (response.has("cod")) {
                Log.i("WeatherMainFragment.java", "JSON object has 'cod' key, 'cod': "
                        + String.valueOf(response.optInt("cod", 400)));
                if ( response.optInt("cod", 400) == 200  ) {
                    return true;
                } else {
                    Log.e("WeatherPrimaryFragment.java",
                            "Looks like cod is not equal to 200. This JSON file has no data.");
                    return false;
                }
            } else {
                Log.d("JSON Response", "JSON Object does not contain 'cod' key");
                return false;
            }
        } else {
            Log.d("JSON Response", "JSON Object contains 'error' key");
            return false;
        }
    }


    /**
     * Connect to the OpenWeatherMap API via Heroku app to obtain
     * current weather data about the queried zipcode in a JSON object
     *
     * @param zipcode the queried zipcode used to obtain weather data on
     */
    public void connectToOpenWeatherMap(final String zipcode,
                                        final WeatherZipcodeViewModel model,
                                        final View view) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/validate_zipcode";
        Log.i("WeatherMainViewModel.java", "Connecting to OpenWeatherMap API current weather conditions");
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                result -> {
                    this.handleResultFromOpenWeatherMap(result, zipcode, model, view);
                },
                error -> {
                    Log.e("CONNECTION ERROR", error.getLocalizedMessage());
                    throw new IllegalStateException(error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", "6543ce89bd26ded32186bae89a6a071e");
                headers.put("zipcode", zipcode);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getContext()).add(request);
    }
}