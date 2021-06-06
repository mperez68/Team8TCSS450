package edu.uw.tcss450.team8tcss450.ui.weather.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherMainFragment;
import edu.uw.tcss450.team8tcss450.ui.weather.WeatherZipcodeViewModel;

/**
 * The page for the weather map
 *
 * @author Brandon Kennedy
 * @version 4 June 2021
 */
public class WeatherMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int MY_PERMISSIONS_LOCATIONS = 8414;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // The view model for the weather map fragment that stores the current location on map
    private WeatherMapViewModel mViewModel;

    private GoogleMap mMap;

    //Use a FusedLocationProviderClient to request the location
    private FusedLocationProviderClient mFusedLocationClient;

    // Will use this call back to decide what to do when a location change is detected
    private LocationCallback mLocationCallback;

    private LocationRequest mLocationRequest;

    /**
     * Constructor for the weather map fragment
     */
    public WeatherMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();

        // prompt the user for permission to access the devices location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        if (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_LOCATIONS);
        } else {
            requestLocation();
        }

        // instantiate an anonymous object to handle the callback when a location update is available
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    Log.d("LOCATION UPDATE!", location.toString());
                    if (mViewModel == null) {
                        mViewModel = new ViewModelProvider(activity)
                                .get(WeatherMapViewModel.class);
                    }
                    mViewModel.setLocation(location);
                }
            };
        };
        createLocationRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherMapViewModel.class);
        mViewModel.addLocationObserver(getViewLifecycleOwner(), location -> {
            Log.d("WeatherMapFragment.onViewCreated", location.toString());
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //add this fragment as the OnMapReadyCallback -> See onMapReady()
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_LOCATIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // locations-related task you need to do.
                    requestLocation();
                } else { // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("PERMISSION DENIED", "Nothing to see or do here.");

                    //Shut down the app. In production release, you would let the user
                    //know why the app is shutting down...maybe ask for permission again?
                    getActivity().finishAndRemoveTask();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * Requests the device location from the API
     */
    private void requestLocation() {
        FragmentActivity activity = getActivity();

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("REQUEST LOCATION", "User did NOT allow permission to request location!");
        } else {
            mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d("LOCATION", location.toString());
                            if (mViewModel == null) {
                                mViewModel = new ViewModelProvider(activity)
                                        .get(WeatherMapViewModel.class);
                            }
                            mViewModel.setLocation(location);
                        }
                    }
                });
        }
    }

    /**
     * Create and configure a Location Request used when retrieving location updates
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        WeatherMapViewModel model = new ViewModelProvider(getActivity())
                .get(WeatherMapViewModel.class);
        model.addLocationObserver(getViewLifecycleOwner(), location -> {
            if(location != null) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMyLocationEnabled(true);
                final LatLng c = new LatLng(location.getLatitude(), location.getLongitude());
                //Zoom levels are from 2.0f (zoomed out) to 21.f (zoomed in)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 15.0f));
            }
        });

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Log.d("LAT/LONG", latLng.toString());
        mMap.clear();
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        latLng, mMap.getCameraPosition().zoom));
        this.connectToOpenWeatherMap(latLng);
    }

    /**
     * If connection to the OpenWeatherMap API is successful, a JSONObject
     * is retrieved from it and is handled for validation.
     *
     * @param response the JSONObject retrieved from OpenWeatherMap API
     * @param latLng the latitude/longitude coordinates in which the JSONObject was retrieved for
     */
    private void handleResultFromOpenWeatherMap(final JSONObject response,
                                                final LatLng latLng) {
        if (validateRetrievedJSONObject(response)) {
            try {
                IntFunction<String> getString =
                        getActivity().getResources()::getString;
                WeatherZipcodeViewModel model = new ViewModelProvider(
                        getActivity()).get(WeatherZipcodeViewModel.class);
                String city = response.getString(getString.apply(R.string.keys_json_weathermap_name));
                String latitude = String.valueOf(latLng.latitude);
                String longitude = String.valueOf(latLng.longitude);
                Log.i("WeatherMapFragment.java", "Zipcode from search query is valid");
                Log.i("WeatherMapFragment.java",
                        "City= " + city + ", " +
                                "Latitude/Longitude=" + latitude + "," + longitude);

                // Set the new WeatherZipcodeViewModel with the newest
                // validated lat/long coordinates and city location name.
                // Thus, display the new location city name on WeatherMainFragment
                model.setCity(city);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(city));
                model.setLocation(latitude, longitude);

                WeatherMainFragment parent = (WeatherMainFragment) WeatherMapFragment.this.getParentFragment();
                TextView cityName = parent.getView().findViewById(R.id.zipcode_queried_location);
                cityName.setText(model.getCity());
                parent.refreshWeatherDisplay(parent.getView());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("ERROR!", e.getMessage());
            }
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
        IntFunction<String> getString =
                getActivity().getResources()::getString;
        if (response.length() > 0 && !response.has(getString.apply(R.string.keys_json_weathermap_error))) {
            Log.i("WeatherMapFragment.java", "We have a JSON. Now we open it to look for 'cod' key");
            if (response.has(getString.apply(R.string.keys_json_weathermap_cod))) {
                Log.i("WeatherMapFragment.java", "JSON object has 'cod' key, 'cod': "
                        + String.valueOf(response.optInt(getString.apply(R.string.keys_json_weathermap_cod), 400)));
                if ( response.optInt(getString.apply(R.string.keys_json_weathermap_cod), 400) == 200  ) {
                    return true;
                } else {
                    Log.e("WeatherMapFragment.java",
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
     * @param latLng the latitude/longitude coordinates obtained from a click on the map
     */
    private void connectToOpenWeatherMap(final LatLng latLng) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/validate_lat_lon";
        Log.i("WeatherMapModel.java", "Connecting to OpenWeatherMap API current weather conditions");
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                result -> {
                    this.handleResultFromOpenWeatherMap(result, latLng);
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
                headers.put("latitude", String.valueOf(latLng.latitude));
                headers.put("longitude", String.valueOf(latLng.longitude));
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