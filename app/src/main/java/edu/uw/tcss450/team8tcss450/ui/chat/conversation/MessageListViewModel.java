package edu.uw.tcss450.team8tcss450.ui.chat.conversation;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

public class MessageListViewModel extends AndroidViewModel {
    // TODO DELETE LATER
    private final String mTempMessage = "Bacon ipsum dolor amet short ribs meatloaf chuck chislic capicola. ";
     private MutableLiveData<List<Message>> mMessageList;
    public MessageListViewModel(@NonNull Application application) {
        super(application);
        mMessageList = new MutableLiveData<>();
        mMessageList.setValue(new ArrayList<>());
    }
    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<Message>> observer) {
        mMessageList.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        //Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        //throw new IllegalStateException(error.getMessage());      // TODO fix when server is set up
        handleResult(null);
    }

    private void handleResult(final JSONObject result) {
        mMessageList.setValue(new ArrayList<>());
        for(int i = 0; i < 10; i++) {    // TODO remove when live data is implemented
            Message post = new Message(
                    "My Dearest Friend #?", mTempMessage + (i + 1));
            if (!mMessageList.getValue().contains(post)) {
                mMessageList.getValue().add(post);
            }
        }
        mMessageList.setValue(mMessageList.getValue());
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
