package edu.uw.tcss450.team8tcss450.ui.chat.conversation;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.io.RequestQueueSingleton;

/**
 * A view model that stores the states of the sent chats.
 */
public class ChatSendViewModel extends AndroidViewModel {

    private final MutableLiveData<JSONObject> mResponse;

    /**
     * ChatTestSendViewModel constructor
     *
     * @Param theApplication
     */
    public ChatSendViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Register as an observer to listen to a specific response from a json object.
     * @param theOwner the fragments lifecycle owner
     * @param theObserver the observer
     */
    public void addResponseObserver(@NonNull LifecycleOwner theOwner,
                                    @NonNull Observer<? super JSONObject> theObserver) {
        mResponse.observe(theOwner, theObserver);
    }

    /**
     * Requests the post endpoint in messages.
     * @param theChatID
     * @param theJwt
     * @param theMessage
     */
    public void sendMessage(final int theChatID, final String theJwt, final String theMessage) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "messages";

        JSONObject body = new JSONObject();
        try {
            body.put("message", theMessage);
            body.put("chatId", theChatID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //push token found in the JSONObject body
                mResponse::setValue, // we get a response but do nothing with it
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
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }


    /**
     * Error handler if unexpected or error returned from post endpoint in messages.
     * @param theError
     */
    private void handleError(final VolleyError theError) {
        if (Objects.isNull(theError.networkResponse)) {
            Log.e("NETWORK ERROR", theError.getMessage());
        }
        else {
            String data = new String(theError.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    theError.networkResponse.statusCode +
                            " " +
                            data);
        }
    }

}
