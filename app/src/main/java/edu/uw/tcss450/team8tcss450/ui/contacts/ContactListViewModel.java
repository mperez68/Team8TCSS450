package edu.uw.tcss450.team8tcss450.ui.contacts;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.tcss450.team8tcss450.R;

/**
 * A view model for the contact fragment to keep myContactList as a mutable live data object.
 */
public class ContactListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Contact>> mContactList;
    private ContactsRecyclerViewAdapter mViewAdapter;


    /**
     * Constructor the the contact list view model to instantiate instance fields.
     *
     * @param theApplication
     */
    public ContactListViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        mViewAdapter = new ContactsRecyclerViewAdapter(mContactList.getValue());
    }

    /**
     * Method that creates an observer for myContactList.
     *
     * @param theOwner
     * @param theObserver
     */
    public void addContactListObserver(@NonNull LifecycleOwner theOwner,
                                    @NonNull Observer<? super List<Contact>> theObserver) {
        mContactList.observe(theOwner, theObserver);
    }

    /**
     * Get the contactList.
     *
     * @return myContactList
     */
    public MutableLiveData<List<Contact>> getContactList() {
        return mContactList;
    }

    /**
     * Get view adapter
     *
     * @return myViewAdapter
     */
    public ContactsRecyclerViewAdapter getViewAdapter() {

        return mViewAdapter;
    }





    /**
     * connect to endpoints using heroku app link. Can use get requests from endpoint.
     *
     * @param theJwt the jason web token to connect to
     * @param theEmail
      */
    public void connectGet(String theEmail, String theJwt) {
        String url = "https://team8-tcss450-app.herokuapp.com/contacts/" + theEmail;
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
            if (root.has(getString.apply(R.string.keys_json_contacts))) {
                JSONArray response = root.getJSONArray(getString.apply(R.string.keys_json_contacts));
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonContact = response.getJSONObject(i);
                    Contact contact = new Contact.Builder(
                            jsonContact.getString(
                                    getString.apply(R.string.keys_json_contact_firstname)),
                            jsonContact.getString(
                                    getString.apply(R.string.keys_json_contact_lastname)),
                            jsonContact.getString(
                                    getString.apply(R.string.keys_json_contact_nickname)),
                            jsonContact.getString(
                                    getString.apply(R.string.keys_json_contact_email)))
                            .build();
                    mContactList.getValue().add(contact);
                }
            } else {
                Log.e("ERROR!", "No data array");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }

        mContactList.setValue(mContactList.getValue());

        /*for (int i = 1; i <= 10; i++) {
            Contact contact = new Contact.Builder(
                    "Contact #" + i, "LastName" + i, "Nickname" + i, "testEmail" + i + "@test.edu")
                    .build();

            if (!mContactList.getValue().contains(contact)) {
                mContactList.getValue().add(contact);
            }
        }

        mContactList.setValue(mContactList.getValue());*/
    }

    private void handleGetError(VolleyError error) {
        mContactList.setValue(new ArrayList<>());
    }
}