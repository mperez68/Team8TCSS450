package edu.uw.tcss450.team8tcss450.ui.settings;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import edu.uw.tcss450.team8tcss450.io.RequestQueueSingleton;

public class ChangePasswordViewModel extends AndroidViewModel {

    private MutableLiveData<JSONObject> myResponse;

    /**
     * Constructor for the view model.
     *
     * @param theApplication inherited from AndroidViewModel.
     *
     */
    public ChangePasswordViewModel(@NonNull Application theApplication) {
        super(theApplication);
        myResponse = new MutableLiveData<>();
        myResponse.setValue(new JSONObject());
    }

    /**
     * An observer that observers the mutable live data for changes.
     *
     * @param theOwner
     * @param theObserver the mutablelivedata object to be observed.
     */
    public void addResponseObserver(@NonNull LifecycleOwner theOwner,
                                    @NonNull Observer<? super JSONObject> theObserver) {
        myResponse.observe(theOwner, theObserver);
    }

    /**
     * Error handling for the connection.
     *
     * @param theError
     */
    private void handleError(final VolleyError theError) {
        if (Objects.isNull(theError.networkResponse)) {
            try {
                myResponse.setValue(new JSONObject("{" +
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
                myResponse.setValue(response);
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }

    /**
     * connect to endpoints using heroku app link. Can use get requests from endpoint.
     *
     * @param theEmail the client email
     * */
    public void connect(final String theEmail,
                        final String theOldPassword,
                        final String theNewPassword) {
        // TODO encode better
        String e1 = theEmail.substring(0,theEmail.indexOf("@"));
        String e2 = theEmail.substring(theEmail.indexOf("@") + 1,theEmail.indexOf("."));
        String e3 = theEmail.substring(theEmail.indexOf(".") + 1);
        String url = "https://team8-tcss450-app.herokuapp.com/newpw?e1=" + e1 + "&e2=" + e2 + "&e3=" + e3;
        JSONObject body = new JSONObject();
        try {
            body.put("oldpass", theOldPassword);
            body.put("password", theNewPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                myResponse::setValue,
                this::handleError);

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);

        //Instantiate the RequestQueue and add the request to the queue
//        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
//                .addToRequestQueue(request);
    }
}
