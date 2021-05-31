package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

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
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;

public class ContactSearchViewModel extends AndroidViewModel {
    private MutableLiveData<JSONObject> mResponse;
    private boolean mSearch;

    /**
     * Constructor the the contact list view model to instantiate instance fields.
     *
     * @param theApplication
     */
    public ContactSearchViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
        mSearch = false;
    }

    public boolean getSearch() {
        return mSearch;
    }

    /**
     * connect to endpoints using heroku app link. Can use get requests from endpoint.
     *
     * @param theJwt the json web token to connect to
     * @param theEmail
     */
    public void connectGet(String theEmail, String theJwt) {
        String url = "https://team8-tcss450-app.herokuapp.com/search/" + theEmail;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleGetResult,
                this::handleGetError) {
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

    private void handleGetResult(final JSONObject theResult) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            JSONObject root = theResult;
            if(root.has(getString.apply(R.string.keys_json_success))) {
                mSearch = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
    }

    private void handleGetError(VolleyError theError) {
        mSearch = false;
    }
}
