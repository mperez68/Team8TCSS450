package edu.uw.tcss450.team8tcss450.ui.contacts.requests;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.contacts.Contact;

public class ContactRequestsTabViewModel extends AndroidViewModel {

    private MutableLiveData<List<ContactRequest>> mRequestList;
    private MutableLiveData<JSONObject> mResponse;
    private ContactRequestsTabRecyclerViewAdapter mViewAdapter;

    /**
     * Constructor the the contact list view model to instantiate instance fields.
     *
     * @param theApplication
     */
    public ContactRequestsTabViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mRequestList = new MutableLiveData<>();
        mRequestList.setValue(new ArrayList<>());
        mViewAdapter = new ContactRequestsTabRecyclerViewAdapter(mRequestList.getValue());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Method that creates an observer for myContactList.
     *
     * @param theOwner
     * @param theObserver
     */
    public void addContactRequestObserver(@NonNull LifecycleOwner theOwner,
                                       @NonNull Observer<? super List<ContactRequest>> theObserver) {
        mRequestList.observe(theOwner, theObserver);
    }

    /**
     * Get the contactList.
     *
     * @return myContactList
     */
    public MutableLiveData<List<ContactRequest>> getRequestList() {
        return mRequestList;
    }

    /**
     * Get view adapter
     *
     * @return myViewAdapter
     */
    public ContactRequestsTabRecyclerViewAdapter getViewAdapter() {
        return mViewAdapter;
    }

    public void connectPost(String theEmail, String theJwt) {
        String url = "https://team8-tcss450-app.herokuapp.com/requests/";
        JSONObject body = new JSONObject();
        try {
            body.put("email", theEmail);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //no body for this get request
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

    /**
     * connect to endpoints using heroku app link. Can use get requests from endpoint.
     *
     * @param theJwt the jason web token to connect to
     * @param theEmail
     */
    public void connectGet(String theEmail, String theJwt) {
        String url = "https://team8-tcss450-app.herokuapp.com/requests/" + theEmail;
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

    /**
     * Method that creates dummy data for the recycler view.
     *
     * @param theResult a JSONObject to be used in the future.
     */
    private void handleGetResult(final JSONObject theResult) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            JSONObject root = theResult;
            if (root.has(getString.apply(R.string.keys_json_requests))) {
                JSONArray response = root.getJSONArray(getString.apply(R.string.keys_json_requests));
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonContact = response.getJSONObject(i);
                    ContactRequest contactRequest = new ContactRequest.Builder(
                            jsonContact.getString(
                                    getString.apply(R.string.keys_json_contact_email)))
                            .build();
                    mRequestList.getValue().add(contactRequest);
                }
            } else {
                Log.e("ERROR!", "No data array");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }

        mRequestList.setValue(mRequestList.getValue());
    }

    private void handleGetError(VolleyError error) {
        mRequestList.setValue(new ArrayList<>());
    }

    public void connectDelete(String theEmail, String theJwt) {
        String url = "https://team8-tcss450-app.herokuapp.com/requests/" + theEmail;
        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null, //no body for this get request
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

        List<ContactRequest> tempList = mRequestList.getValue();
        ContactRequest tempRequest = null;

        for (ContactRequest r : tempList) {
            if (r.getEmail() == theEmail) {
                tempRequest = r;
            }
        }

        tempList.remove(tempRequest);
        mRequestList.setValue(tempList);
    }

    private void handleError(VolleyError theError) {
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
}
