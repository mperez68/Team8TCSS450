package edu.uw.tcss450.team8tcss450.ui.chat;

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

public class ChatListViewModel extends AndroidViewModel {
    // TODO DELETE LATER
    private final String mTempMessage = "    Bacon ipsum dolor amet short ribs meatloaf chuck chislic capicola. Pork belly turkey ham spare ribs frankfurter brisket ball tip, pork loin flank drumstick turducken capicola andouille tenderloin beef ribs. Short loin kielbasa picanha tail pancetta. Chicken chislic pork chop landjaeger brisket beef ribs burgdoggen boudin andouille meatball pancetta. Meatloaf beef ribs pig, leberkas bacon burgdoggen beef shoulder t-bone short ribs kielbasa turkey cow spare ribs ball tip.\n" +
            System.getProperty("line.separator") +
            "    Ham pastrami pork chop picanha. Spare ribs salami cupim alcatra, flank tail jerky pig swine filet mignon ball tip buffalo sausage venison pork chop. Flank buffalo cupim, filet mignon tri-tip turkey sirloin ham hock frankfurter spare ribs pig beef ribs. Pancetta sausage meatloaf, brisket tongue chislic salami jowl kielbasa porchetta andouille. Shank t-bone pork belly brisket pork chop. Ball tip flank shankle andouille, alcatra spare ribs turducken kielbasa picanha meatball shank boudin landjaeger. Landjaeger bresaola swine pork kevin pig prosciutto.";
    private MutableLiveData<List<ChatConversation>> mChatList;
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }
    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatConversation>> observer) {
        mChatList.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            JSONObject root = result;
            if (root.has(getString.apply(R.string.keys_json_blogs_response))) {
                JSONObject response =
                        root.getJSONObject(getString.apply(
                                R.string.keys_json_blogs_response));
                if (response.has(getString.apply(R.string.keys_json_blogs_data))) {
//                    JSONArray data = response.getJSONArray(
//                            getString.apply(R.string.keys_json_blogs_data));
//                    for(int i = 0; i < data.length(); i++) {
                    for(int i = 0; i < 4; i++) {    // TODO remove when live data is implemented
//                        JSONObject jsonBlog = data.getJSONObject(i);
                        edu.uw.tcss450.team8tcss450.ui.chat.ChatConversation post = new edu.uw.tcss450.team8tcss450.ui.chat.ChatConversation.Builder(
                        "My Dearest Friend #" + (i + 1), mTempMessage)
                                 //.addMessage(System.getProperty("line.separator") + "Here's the second part of the message!")  // TODO change to message object pt. 2
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
                        if (!mChatList.getValue().contains(post)) {
                            mChatList.getValue().add(post);
                        }
                    }
                } else {
                    Log.e("ERROR!", "No data array");
                }
            } else {
                Log.e("ERROR!", "No response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mChatList.setValue(mChatList.getValue());
    }

    public void connectGet(String jwt) {
        String url =
                "https://cfb3-tcss450-labs-2021sp.herokuapp.com/phish/blog/get";    // TODO change to live data source
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
