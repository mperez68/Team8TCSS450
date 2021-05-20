package edu.uw.tcss450.team8tcss450.ui.weather;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The view model for WeatherMainFragment that handles
 * a queried zipcode and determines whether or not it is
 * valid.  If it is valid, a connection is made to a
 * weather API to retrieve current weather condition data.
 *
 * @author Brandon Kennedy
 * @version 19 May 2021
 */
public class WeatherMainViewModel extends AndroidViewModel {

    // The JSON object that is retrieved from the OpenWeatherMap API
    private MutableLiveData<JSONObject> mResponse;

    // The queried zipcode to be used to get needed weather data from API
    private String mZipcode;

    // The city associated with the zipcode
    //private String mCity;

    /**
     * Construct the view model
     */
    public WeatherMainViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Set and store the queried zipcode
     * @param zipcode the queried zipcode
     */
    public void setZipcode(String zipcode) {
        this.mZipcode = zipcode;
    }

    /**
     * Get the zipcode that's stored in this inner class
     * @return the stored queried zipcode
     */
    public String getZipcode() {
        return this.mZipcode;
    }


    /**
     * Add a response observer for the view model to handle data
     * retrieved from the OpenWeatherMap API.
     *
     * @param owner the owner of the lifecycle that controls this observer
     * @param observer the official observer of this view model
     */
    public void addObserver(@NonNull LifecycleOwner owner,
                            @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }


    /**
     * In case attempt to connect to OpenWeatherMap API and
     * obtain data from it goes wrong, deliver an appropriate
     * handling on this failed attempt
     *
     * @param error the Volley error being handled
     */
    private void handleError(final VolleyError error) {
        Log.e("WeatherMainViewModel.java", "Error in getting response from WeatherBit API.");
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON Parse", "JSON Parse Error in handleError");
            }
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                .replace('\"', '\'');
            try {
                JSONObject response = new JSONObject();
                response.put("code", error.networkResponse.statusCode);
                response.put("data", new JSONObject(data));
                mResponse.setValue(response);
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }

    /**
     * Connect to the WeatherBit API via Heroku app to obtain
     * current weather data about the queried zipcode in a JSON object
     *
     * @param zipcode the queried zipcode used to obtain weather data on
     */
    public void connect(final String zipcode) {
        String url = "https://team8-tcss450-app.herokuapp.com/weather/current";
        Log.i("WeatherMainViewModel.java", "Connecting to OpenWeatherMap API current weather conditions");
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                mResponse::setValue,
                this::handleError) {
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
        Volley.newRequestQueue(getApplication().getApplicationContext())
            .add(request);
    }

}
