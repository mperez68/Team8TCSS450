package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.app.Application;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatConversation;

public class ContactListViewModel extends AndroidViewModel {
    private MutableLiveData<List<Contact>> myContactList;
    public ContactListViewModel(@NonNull Application application) {
        super(application);
        myContactList = new MutableLiveData<>();
        myContactList.setValue(new ArrayList<>());
    }
    public void addContactListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<Contact>> observer) {
        myContactList.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        //Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        //throw new IllegalStateException(error.getMessage());      // TODO fix when server is set up
        handleResult(null);
    }

    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
//        try {
//            JSONObject root = result;
//            if (root.has(getString.apply(R.string.keys_json_blogs_response))) {
//                JSONObject response =
//                        root.getJSONObject(getString.apply(
//                                R.string.keys_json_blogs_response));
//                if (response.has(getString.apply(R.string.keys_json_blogs_data))) {
//                    JSONArray data = response.getJSONArray(
//                            getString.apply(R.string.keys_json_blogs_data));
//                    for(int i = 0; i < data.length(); i++) {
        for(int i = 0; i < 10; i++) {    // TODO test 10 rows of data
//                        JSONObject jsonBlog = data.getJSONObject(i);
            Contact contact = new Contact.Builder(
                    "Contact #" + (i + 1), "testUsername", "testEmail@test.edu")
                    //.addMessage(System.getProperty("line.separator") + "Here's the second part of the message!")  // TODO change to object.
//                                jsonBlog.getString(
//                                        getString.apply(
//                                                R.string.keys_json_blogs_pubdate)),
//                                jsonBlog.getString(
//                                        getString.apply(
//                                                R.string.keys_json_blogs_title)))
//                                .addTeaser(jsonBlog.getString(
//                                        getString.apply(
//                                                R.string.keys_json_blogs_teaser)))
//                                .addUrl(jsonBlog.getString(
//                                        getString.apply(
//                                                R.string.keys_json_blogs_url)))   // TODO add live data
                    .build();
            if (!myContactList.getValue().contains(contact)) {
                myContactList.getValue().add(contact);
            }
        }
//                } else {
//                    Log.e("ERROR!", "No data array");
//                }
//            } else {
//                Log.e("ERROR!", "No response");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("ERROR!", e.getMessage());
//        }
        myContactList.setValue(myContactList.getValue());
    }

    public void connectGet(String jwt) {
        String url =
                "https://team8-tcss450-app.herokuapp.com/auth";    // TODO change to live data source
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
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

