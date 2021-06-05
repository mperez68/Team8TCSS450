package edu.uw.tcss450.team8tcss450.ui.home;

import android.app.Application;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * A view model for the sign in fragment to keep myResponse as a mutable live data object.
 */
public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> mNotifList;
    private MutableLiveData<JSONObject> mResponse;

    /**
     * Constructor for the view model.
     *
     * @param theApplication inherited from AndroidViewModel.
     *
     */
    public HomeViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mNotifList = new MutableLiveData<>();
        mNotifList.setValue(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    public void addNotifListObserver(@NonNull LifecycleOwner theOwner,
                                       @NonNull Observer<? super List<String>> theObserver) {
        mNotifList.observe(theOwner, theObserver);
    }

    /**
     * An observer that observers the mutable live data for changes.
     *
     * @param theOwner
     * @param theObserver the mutablelivedata object to be observed.
     */
    public void addResponseObserver(@NonNull LifecycleOwner theOwner,
                                    @NonNull Observer<? super JSONObject> theObserver) {
        if (mResponse.hasObservers()) mResponse.removeObservers(theOwner);
        else mResponse.observe(theOwner, theObserver);
    }

    /**
     * Error handling for the connection.
     *
     * @param theError
     */
    private void handleError(final VolleyError theError) {
        if (Objects.isNull(theError.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + theError.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
        else {
            String data = new String(theError.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                JSONObject response = new JSONObject();
                response.put("code", theError.networkResponse.statusCode);
                response.put("data", new JSONObject(data));
                mResponse.setValue(response);
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }

    public void connectGet(String theJwt) {
        String url = "https://team8-tcss450-app.herokuapp.com/notifs";
        JSONObject body = new JSONObject();
        try {
            //body.put("email", theEmail);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                body,
                mResponse::setValue,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", theJwt);
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

    public void connectPost(final String theType, final String theJwt) {
        String url = "https://team8-tcss450-app.herokuapp.com/notifs";
        JSONObject body = new JSONObject();
        try {
            body.put("type", theType);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                mResponse::setValue,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", theJwt);
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