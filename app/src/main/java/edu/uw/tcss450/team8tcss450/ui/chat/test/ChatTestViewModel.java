package edu.uw.tcss450.team8tcss450.ui.chat.test;

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

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.io.RequestQueueSingleton;

/**
 * A view model that stores teh state of the chats.
 */
public class ChatTestViewModel extends AndroidViewModel {

    /**
     * A Map of Lists of Chat Messages.
     * The Key represents the Chat ID
     * The value represents the List of (known) messages for that that room.
     */
    private Map<Integer, MutableLiveData<List<ChatTestMessage>>> mMessages;

    private MutableLiveData<Integer> myChatID;

    private MutableLiveData<JSONObject> mResponse;

    /**
     * Constructor for the ChatTestViewModel
     *
     * @param theApplication
     */
    public ChatTestViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mMessages = new HashMap<>();

        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());

        //chatID
        myChatID = new MutableLiveData<>();
        myChatID.setValue(-1);
    }

    /**
     * Register as an observer to listen to a specific chat room's list of messages.
     * @param theChatId the chatid of the chat to observer
     * @param theOwner the fragments lifecycle owner
     * @param theObserver the observer
     */
    public void addMessageObserver(int theChatId,
                                   @NonNull LifecycleOwner theOwner,
                                   @NonNull Observer<? super List<ChatTestMessage>> theObserver) {
        getOrCreateMapEntry(theChatId).observe(theOwner, theObserver);
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

    //not needed for now
    public void addChatIDObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        myChatID.observe(owner, observer);
    }

    /**
     * Return a reference to the List<> associated with the chat room. If the View Model does
     * not have a mapping for this chatID, it will be created.
     *
     * WARNING: While this method returns a reference to a mutable list, it should not be
     * mutated externally in client code. Use public methods available in this class as
     * needed.
     *
     * @param theChatID the id of the chat room List to retrieve
     * @return a reference to the list of messages
     */
    public List<ChatTestMessage> getMessageListByChatId(final int theChatID) {
        return getOrCreateMapEntry(theChatID).getValue();
    }

    /**
     * Gets or creates a map of integer to arraylist for the chats based on the chatID
     *
     * @param theChatID
     */
    private MutableLiveData<List<ChatTestMessage>> getOrCreateMapEntry(final int theChatID) {
        if(!mMessages.containsKey(theChatID)) {
            mMessages.put(theChatID, new MutableLiveData<>(new ArrayList<>()));
        }
        return mMessages.get(theChatID);
    }

    /**
     * Makes a request to the web service to get the first batch of messages for a given Chat Room.
     * Parses the response and adds the ChatTestMessage object to the List associated with the
     * ChatRoom. Informs observers of the update.
     *
     * Subsequent requests to the web service for a given chat room should be made from
     * getNextMessages()
     *
     * @param theChatID the chatroom id to request messages of
     * @param theJwt the users signed JWT
     */
    public void getFirstMessages(final int theChatID, final String theJwt) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "messages/" + theChatID;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handelSuccess,
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

        //code here will run
    }

    /**
     * Makes a request to the web service to get the next batch of messages for a given Chat Room.
     * This request uses the earliest known ChatTestMessage in the associated list and passes that
     * messageId to the web service.
     * Parses the response and adds the ChatTestMessage object to the List associated with the
     * ChatRoom. Informs observers of the update.
     *
     * Subsequent calls to this method receive earlier and earlier messages.
     *
     * @param theChatID the chatroom id to request messages of
     * @param theJwt the users signed JWT
     */
    public void getNextMessages(final int theChatID, final String theJwt) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "messages/" +
                theChatID +
                "/" +
                mMessages.get(theChatID).getValue().get(0).getMessageId();

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handelSuccess,
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

        //code here will run
    }

    /**
     * When a chat message is received externally to this ViewModel, add it
     * with this method.
     * @param theChatID
     * @param theMessage
     */
    public void addMessage(final int theChatID, final ChatTestMessage theMessage) {
        List<ChatTestMessage> list = getMessageListByChatId(theChatID);
        list.add(theMessage);
        getOrCreateMapEntry(theChatID).setValue(list);
    }

    private void handelSuccess(final JSONObject response) {
        List<ChatTestMessage> list;
        if (!response.has("chatId")) {
            throw new IllegalStateException("Unexpected response in ChatViewModel: " + response);
        }
        try {
            list = getMessageListByChatId(response.getInt("chatId"));
            JSONArray messages = response.getJSONArray("rows");
            for(int i = 0; i < messages.length(); i++) {
                JSONObject message = messages.getJSONObject(i);
                ChatTestMessage cMessage = new ChatTestMessage(
                        message.getInt("messageid"),
                        message.getString("message"),
                        message.getString("email"),
                        message.getString("timestamp")
                );
                if (!list.contains(cMessage)) {
                    // don't add a duplicate
                    list.add(0, cMessage);
                } else {
                    // this shouldn't happen but could with the asynchronous
                    // nature of the application
                    Log.wtf("Chat message already received",
                            "Or duplicate id:" + cMessage.getMessageId());
                }

            }
            //inform observers of the change (setValue)
            getOrCreateMapEntry(response.getInt("chatId")).setValue(list);
        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * Handler method for a volley error for a bad json request.
     * @param theError
     */
    private void handleError(final VolleyError theError) {
        if (Objects.isNull(theError.networkResponse)) {
            Log.e("NETWORK ERROR", theError.getMessage());

            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + theError.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
        else {
            String data = new String(theError.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    theError.networkResponse.statusCode +
                            " " +
                            data);

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

    /**
     * Connects to the endpoint to request a new chat to be created.
     * TODO possible redo with new endpoint.
     * @param theChatRoomName
     * @param theJwt
     */
    public void connectChatID(String theChatRoomName, final String theJwt) {
        String url = "https://team8-tcss450-app.herokuapp.com/chats";
        //print statement for debugging
        JSONObject body = new JSONObject();
        try {
            body.put("name", theChatRoomName); //CHAT ROOM NAME

        } catch (JSONException e) {
            e.printStackTrace();
        }
        int temp;
        Request request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    body,
                    this::handleChatID,
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

        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);


    }

    /**
     * Handles a successful GET request to the chats endpoint. returns the chatID.
     * TODO possible redo with new endpoint.
     * @param theResponse
     */
    private void handleChatID(JSONObject theResponse) {
        int result = -1;
        if (!theResponse.has("chatID")) {
            throw new IllegalStateException("Unexpected response in ChatViewModel: " + theResponse);
        }
        try {
            myChatID.setValue(theResponse.getInt("chatID"));


            //inform observers of the change (setValue)
            //getOrCreateMapEntry(response.getInt("chatId")).setValue(list);
        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle CHATID ChatTestViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * getter for the chat ID. Possibly not needed.
     * @return the chat ID.
     */
    public int getChatID() {
        return myChatID.getValue();
    }
//    public void addUsersToChat(int theChatID, String theChatter, String theJwt) {
//        String url = "https://team8-tcss450-app.herokuapp.com/chats/" + theChatID ;
//        //print statement for debugging
//        JSONObject body = new JSONObject();
//        try {
//            body.put("chatId", theChatID);
//
//            body.put("memberid", theChatter);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        int temp;
//        Request request = new JsonObjectRequest(
//                Request.Method.PUT,
//                url,
//                body,
//                this::handleAddUsers, //nothing for now
//                this::handleError) {
//
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                // add headers <key,value>
//                headers.put("Authorization", theJwt);
//                return headers;
//            }
//        };
//
//
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                10_000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        Volley.newRequestQueue(getApplication().getApplicationContext())
//                .add(request);
//    }
//
//    private void handleAddUsers(JSONObject jsonObject) {
//        Log.e("Success", "PUT User into chat was successful");
//    }
}
