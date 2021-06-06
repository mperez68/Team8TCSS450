package edu.uw.tcss450.team8tcss450.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentHomeBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel mHomeViewModel;
    private UserInfoViewModel mUserInfoViewModel;

    private static final int MY_PERMISSIONS_LOCATIONS = 8414;

    //Use a FusedLocationProviderClient to request the location
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean hasContacts;
    private boolean hasMessages;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate that binds the contact list view model and connects to the get endpoint on the server.
     *
     * @param
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.createNotificationAboutWeather();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUserInfoViewModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        mHomeViewModel = new ViewModelProvider(getActivity())
                .get(HomeViewModel.class);
        mHomeViewModel.connectGet(mUserInfoViewModel.getmJwt());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Local access to the ViewBinding object. No need to create as Instance Var as it is only
        //used here.
        FragmentHomeBinding binding = FragmentHomeBinding.bind(getView());

        binding.textEmail.setText(mUserInfoViewModel.getEmail()); //textEmail will change when fragment is created.


        mHomeViewModel.addResponseObserver(getViewLifecycleOwner(), response -> updateNotificationBell(response));

        binding.imageNotification.setOnClickListener(button -> {
            // TODO show user notifications.
            binding.imageNotification.clearColorFilter();
            if (hasContacts) {
                Navigation.findNavController(getView()).navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationContacts()
                );
                hasContacts = false;
                if (!hasMessages) this.resetContactNotifications();
            } else if (hasMessages) {
                Navigation.findNavController(getView()).navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationChat()
                );
                hasMessages = false;
                this.resetMessageNotifications();
            }
        });
    }

    private void updateNotificationBell(JSONObject response) {
        hasContacts = false;
        hasMessages = false;
        try {
            if (getNumContacts(response) > 0){
                hasContacts = true;
                FragmentHomeBinding.bind(getView()).imageNotification.setColorFilter(Color.RED);
            }
            if (getNumMessages(response) > 0){
                hasMessages = true;
                FragmentHomeBinding.bind(getView()).imageNotification.setColorFilter(Color.RED);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getNumContacts(JSONObject response) throws JSONException{
        JSONArray arr = response.getJSONArray("notifications");
        int count = 0;
        for (int i = 0; i < arr.length(); i++){
            if (arr.get(i).toString().contains("Contact")) count++;
        }
        return count;
    }

    private int getNumMessages(JSONObject response) throws JSONException{
        JSONArray arr = response.getJSONArray("notifications");
        int count = 0;
        for (int i = 0; i < arr.length(); i++){
            if (arr.get(i).toString().contains("Message")) count++;
        }
        return count;
    }

    private void resetContactNotifications(){ mHomeViewModel.connectPost("Contact", mUserInfoViewModel.getmJwt()); }
    private void resetMessageNotifications(){ mHomeViewModel.connectPost("Message", mUserInfoViewModel.getmJwt()); }

    /**
     * Create weather notification by getting data about current weather condition.
     * Begin this by prompting the user for permission to access the devices location
     */
    private void createNotificationAboutWeather() {
        FragmentActivity activity = getActivity();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        // Create a Weather zipcode view model and set a default
        // latitude/longitude coordinate in case GPS location isn't
        // granted permission or device fails to get GPS coordinates
        new ViewModelProvider(
                getActivity(),
                new WeatherZipcodeViewModel.WeatherZipcodeViewModelFactory(
                        "47.2643",
                        "-122.4575"))
                .get(WeatherZipcodeViewModel.class);

        Log.d("HomeFragment.java", "Package name for activity is " + getActivity().getPackageName());

        WeatherZipcodeViewModel model = new ViewModelProvider(
                getActivity()).get(WeatherZipcodeViewModel.class);
        model.setCity("Tacoma");

        if (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.e("HomeFragment.createNotificationAboutWeather()", "Permission not granted by user to access GPS location.");

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_LOCATIONS);

        } else {
            Log.d("HomeFragment.createNotificationAboutWeather()", "Permission has been granted by user to access GPS location.");
            requestLocationForWeather();
        }
    }

    /**
     * Requests the device location from the API
     */
    private void requestLocationForWeather() {
        FragmentActivity activity = getActivity();

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("REQUEST LOCATION", "User did NOT allow permission to request location!");
        } else {
            Log.i("HomeFragment.requestLocationForWeather()", "User has allowed permission to request location");
            mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.i("HomeFragment.requestLocationForWeather().onSuccess()", "onSuccess() begins");
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d("LOCATION", location.toString());
                            WeatherZipcodeViewModel model = new ViewModelProvider(
                                getActivity()).get(WeatherZipcodeViewModel.class);
                            model.setLocation(
                                String.valueOf(location.getLatitude()),
                                String.valueOf(location.getLongitude())
                            );
                            this.connectToOpenWeatherMap(
                                model.getLatitude(),
                                model.getLongitude()
                            );
                        } else {
                            Log.e("HomeFragment.requestLocationForWeather().onSuccess()", "Location is null for some reason");
                        }
                    }

                    /**
                     * If connection to the OpenWeatherMap API is successful,
                     * handle the JSONObject retrieved from that API through
                     * validation and information retrieval.
                     *
                     * @param result the JSONObject retrieved as a successful response from the OpenWeatherMap API
                     */
                    private void handleJSONObjectResult(final JSONObject result) {
                        try {
                            FragmentHomeBinding binding = FragmentHomeBinding.bind(getView());
                            if (!result.getString("name").equals("")) {
                                WeatherZipcodeViewModel model = new ViewModelProvider(
                                        getActivity()).get(WeatherZipcodeViewModel.class);
                                model.setCity(result.getString("name"));
                                binding.weatherLocation.setText(model.getCity());
                            } else {
                                binding.weatherLocation.setText("[Undescribed Location]");
                            }
                            String temperature = String.valueOf(result.getJSONObject("main").getInt("temp"));
                            binding.weatherTemperature.setText(temperature + "\u00B0F");

                            String outlookIcon = result.getJSONArray("weather")
                                    .getJSONObject(0).getString("icon");
                            this.connectToOutlookIcon(outlookIcon);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    /**
                     * Connect to the OpenWeatherMap API via Heroku app to obtain
                     * current weather data about the queried zipcode in a JSON object
                     *
                     * @param latitude the latitude used as a parameter to the call to the OpenWeatherMap API
                     * @param longitude the longitude used as a parameter to the call to the OpenWeatherMap API
                     */
                    private void connectToOpenWeatherMap(String latitude, String longitude) {
                        String url = "https://team8-tcss450-app.herokuapp.com/weather/current/openweathermap";
                        Log.i("HomeFragment.java", "Connecting to OpenWeatherMap API current weather conditions");
                        Request request = new JsonObjectRequest(
                                Request.Method.GET,
                                url,
                                null, //no body for this get request
                                this::handleJSONObjectResult,
                                error -> {
                                    Log.e("CONNECTION ERROR", error.getLocalizedMessage());
                                    throw new IllegalStateException(error.getMessage());
                                }) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<>();
                                // add headers <key,value>
                                headers.put("Authorization", "6543ce89bd26ded32186bae89a6a071e");
                                headers.put("latitude", latitude);
                                headers.put("longitude", longitude);
                                return headers;
                            }
                        };
                        request.setRetryPolicy(new DefaultRetryPolicy(
                                10_000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        //Instantiate the RequestQueue and add the request to the queue
                        Volley.newRequestQueue(getActivity().getApplicationContext())
                                .add(request);
                    }


                    /**
                     * Connect to the WeatherBit API via Heroku app to obtain the
                     * current weather outlook icon JSON object
                     *
                     * @param iconCode the code used to get the specified weather icon image
                     */
                    public void connectToOutlookIcon(final String iconCode) {
                        String url = "https://team8-tcss450-app.herokuapp.com/weather/icon/openweathermap";
                        Request request = new ImageRequest(
                                url,
                                imageResult -> {
                                    FragmentHomeBinding binding = FragmentHomeBinding.bind(getView());
                                    binding.weatherOutlookIcon.setImageBitmap(imageResult);
                                },
                                60,
                                60,
                                ImageView.ScaleType.CENTER,
                                Bitmap.Config.RGB_565,
                                error -> {
                                    Log.e("CONNECTION ERROR", error.getLocalizedMessage());
                                    throw new IllegalStateException(error.getMessage());
                                }) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("icon_code", iconCode);
                                return headers;
                            }
                        };
                        request.setRetryPolicy(new DefaultRetryPolicy(
                                10_000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        //Instantiate the RequestQueue and add the request to the queue
                        Volley.newRequestQueue(getActivity().getApplicationContext())
                                .add(request);
                    }
                });
        }
    }
}