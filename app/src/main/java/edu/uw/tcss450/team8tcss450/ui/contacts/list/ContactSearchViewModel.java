package edu.uw.tcss450.team8tcss450.ui.contacts.list;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.contacts.Contact;
import edu.uw.tcss450.team8tcss450.ui.contacts.requests.ContactRequest;

public class ContactSearchViewModel extends AndroidViewModel {
    private MutableLiveData<JSONObject> mResponse;
    private MutableLiveData<Boolean> mSearch;
    private MutableLiveData<Boolean> mError;

    /**
     * Constructor the the contact list view model to instantiate instance fields.
     *
     * @param theApplication
     */
    public ContactSearchViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());

        mSearch = new MutableLiveData<>();
        mSearch.setValue(new Boolean(false));

        mError = new MutableLiveData<>();
        mError.setValue(new Boolean(false));
    }

    /**
     * Method that creates an observer for myContactList.
     *
     * @param theOwner
     * @param theObserver
     */
    public void searchContactObserver(@NonNull LifecycleOwner theOwner,
                                          @NonNull Observer<? super Boolean> theObserver) {
        mSearch.observe(theOwner, theObserver);
    }

    public void setSearchBoolean(boolean theBool) {
        mSearch.setValue(theBool);
    }

    /**
     * Method that creates an observer for myContactList.
     *
     * @param theOwner
     * @param theObserver
     */
    public void searchErrorObserver(@NonNull LifecycleOwner theOwner,
                                      @NonNull Observer<? super Boolean> theObserver) {
        mError.observe(theOwner, theObserver);
    }

    public void setErrorBoolean(boolean theBool) {
        mError.setValue(theBool);
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
            if (root.has(getString.apply(R.string.keys_json_success))) {
                mSearch.setValue(new Boolean(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
    }

    private void handleGetError(VolleyError theError) {
        mError.setValue(new Boolean(true));
    }
}
