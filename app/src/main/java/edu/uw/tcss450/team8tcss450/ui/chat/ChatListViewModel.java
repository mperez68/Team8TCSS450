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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.ui.chat.test.ChatTestMessage;

import static java.sql.Types.NULL;

/**
 * Modified from Charles Bryan's lab assignment for the course TCSS 450.
 *
 * View Model Class to support the Chat Fragment class. Handles server access features and GET/POST
 * functions.
 * TODO update to live server, include catches for live updates from services
 *
 * @author Charles Bryan
 * @version
 * @author Marc Perez
 * @version 6 May 2021
 */
public class ChatListViewModel extends AndroidViewModel {
    /**
     * Temporary string filler. Generated using BaconIpsum.com
     *  TODO DELETE LATER
     */
    private final String mTempMessage2 = "<Most Recent Chat Here>";

    /**
     * Map of Messages containing the email (index 0), and message (index 1) and timestamp (index 2) and the chatID is key
     */
    private MutableLiveData<Map<Integer, List<String>>> mMessageMap;

    /**
     * List of Conversation objects held by the recycler view.
     */
    private MutableLiveData<List<ChatConversation>> mChatList;

    /**
     * List of Conversation objects held by the recycler view.
     */
    private MutableLiveData<List<String>> myNicknameList;

    /**
     * List of chatIDs for a given email
     */
    private MutableLiveData<List<Integer>> myChatIDs;

    /**
     * Error handling
     */
    private MutableLiveData<JSONObject> mResponse;

    /**
     * This number represents number of items on the recycler view.
     */
    private MutableLiveData<Integer> myMaxNumberOfChatIDs;

    /**
     * This number represents number of items on the recycler view.
     */
    private MutableLiveData<Integer> myTotalChatIDs;

    /**
     * Integer for current chatID number for asynchronous calls
     */
    private int myCurrentChatIdNumber;

    /**
     * Variable to hold the email that the user is logged in as.
     */
    private String myNickname;


    /**
     * public constructor.
     * @param theApplication Application data object.
     */
    public ChatListViewModel(@NonNull Application theApplication) {
        super(theApplication);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());

        myNicknameList = new MutableLiveData<>();
        myNicknameList.setValue(new ArrayList<>());

        myChatIDs = new MutableLiveData<>();
        myChatIDs.setValue(new ArrayList<>());

        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());

        myMaxNumberOfChatIDs = new MutableLiveData<>();
        myMaxNumberOfChatIDs.setValue(0);

        myTotalChatIDs = new MutableLiveData<>();
        myTotalChatIDs.setValue(0);

        mMessageMap = new MutableLiveData<>();
        mMessageMap.setValue(new HashMap<>());

        myNickname = "";

        //no observer
//        myAsynchronousCallCount = new MutableLiveData<>();
//        myAsynchronousCallCount.setValue(0);
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
     * Listener for the each chatID's most recent message.
     * @param owner
     * @param observer
     */
    public void addMessageMapObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super Map<Integer, List<String>>> observer) {
        mMessageMap.observe(owner, observer);
    }

    /**
     * TODO fill in this javadoc
     * @param owner
     * @param observer
     */
    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatConversation>> observer) {
        mChatList.observe(owner, observer);
    }

    /**
     * TODO fill in this javadoc
     * @param owner
     * @param observer
     */
    public void addNicknameListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<String>> observer) {
        myNicknameList.observe(owner, observer);
    }

    /**
     * Listener for the list of chatIDs
     * @param owner
     * @param observer
     */
    public void addChatIDListObserver(@NonNull LifecycleOwner owner,
                                     @NonNull Observer<? super List<Integer>> observer) {
        myChatIDs.observe(owner, observer);
    }

    /**
     * Listener for the currentChatID
     * @param owner
     * @param observer
     */
    public void addCurrentChatIDObserver(@NonNull LifecycleOwner owner,
                                      @NonNull Observer<? super Integer> observer) {
        myMaxNumberOfChatIDs.observe(owner, observer);
    }

    public int getTotalNumberOfChatIDs() {
        return myTotalChatIDs.getValue();
    }

    public List<Integer> getChatIDs() {
        return myChatIDs.getValue();
    }

    public void setTotalNumberOfChatIDs(int theNumber) {
        myTotalChatIDs.setValue(theNumber);
    }

    public void clearNicknameList() {
        myNicknameList.setValue(new ArrayList<>());
    }
    public void clearChatList() {
        mChatList.setValue(new ArrayList<>());
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
     * Received and interprets data sent from Volley.
     * TODO this is hard coded for the 4 chat rooms. Assigns chat room based on email string. In the future use a new endpoint. Original Handle result down below
     * @param theResult A JSONObject generated by Volley with valid data.
     */
    private void handleUsernames(final JSONObject theResult) {

        String contactNickname = "";
        String contactEmail = "";
        String userEmail = "";
        String emailOfLastSender = "";
        String chatMessage = "";
        String chatTimestamp = "";
        int currentChatID = -1;

        // recycler duplication issue.

        if (myCurrentChatIdNumber == myMaxNumberOfChatIDs.getValue()) {
           //mChatList.setValue(new ArrayList<>());

            myCurrentChatIdNumber = 1;
        } else {
            myCurrentChatIdNumber++;
        }



        try {
            //LEFT OFF HERE. WHAT IF THE MAP WAS A STRING INSTEAD OF INTEGER, THEN WE COULD USE THE EMAILS TO DETERMINE THE CHAT ID???
            JSONArray messages = theResult.getJSONArray("rows");
            currentChatID = theResult.getInt("chatId");
            for(int i = 0; i < messages.length(); i++) {
                JSONObject message = messages.getJSONObject(i);
                String nickname = message.getString("nickname");
                String email = message.getString("email");

                if (!(nickname.equals(myNickname))) {
                    contactNickname += nickname;
                    contactEmail += email;
                } else {
                    userEmail += email;
                }
            }

            //currentChatID = myChatIDs.getValue().get(myCurrentChatIdNumber - 1); //-1 because increment has already happened.
            if (mMessageMap.getValue().containsKey(currentChatID)) {

                List<String> chatMessageInfo = mMessageMap.getValue().get(currentChatID);
                emailOfLastSender = chatMessageInfo.get(0); //index 0 chat ID
                chatMessage = chatMessageInfo.get(1); //index 1 is most recent message.
                chatTimestamp = chatMessageInfo.get(2); //index 2 is the timestamp.
            }



        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handleUsernames in ChatListViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
            //create post to add to a list of post. (Listener registered in ChatFragment)
        if ((!(myNicknameList.getValue().contains(contactNickname)))) {
            ChatConversation post = new edu.uw.tcss450.team8tcss450.ui.chat.ChatConversation.Builder(
                    contactNickname, contactEmail, chatMessage, chatTimestamp, currentChatID)

                    .build();


                mChatList.getValue().add(post);
                myNicknameList.getValue().add(contactNickname);
                mChatList.setValue(mChatList.getValue());
        }


    }

    /**
     * Received and interprets list of chatIDs data sent from Volley.
     * @param theResult A JSONObject generated by Volley with valid data.
     */
    private void handleListOfChatIDs(JSONObject theResult) {
        myChatIDs.setValue(new ArrayList<>());
        if (!theResult.has("rows")) {
            throw new IllegalStateException("Unexpected ChatListViewModel: " + theResult);
        }
        try {
            JSONArray rows = theResult.getJSONArray("rows");
            myMaxNumberOfChatIDs.setValue(rows.length());
            myTotalChatIDs.setValue(rows.length());
            for(int i = 0; i < rows.length(); i++) {
                JSONObject responseObj = (JSONObject) rows.get(i);
                int chatID = (int) responseObj.get("chatid");
                myChatIDs.getValue().add(chatID);
            }
            myChatIDs.setValue(myChatIDs.getValue());



        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle CHATID ChatTestViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * Received and interprets
     * @param theResult A JSONObject generated by Volley with valid data.
     */
    private void handleNickname(JSONObject theResult) {
        if (!theResult.has("nickname")) {
            throw new IllegalStateException("Unexpected ChatListViewModel: " + theResult);
        }
        try {
            myNickname = theResult.getString("nickname");



        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle CHATID ChatTestViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * Received and interprets
     * @param theResult A JSONObject generated by Volley with valid data.
     */
    private void handleMessages(JSONObject theResult) {
        if (!(theResult.has("chatId") && theResult.has("rowCount") && theResult.has("rows"))) {
            throw new IllegalStateException("Unexpected ChatListViewModel: " + theResult);
        }
        try {
            JSONArray dataList = theResult.getJSONArray("rows");
            int chatID = theResult.getInt("chatId");
            String message = "";
            String timestamp = "";
            String email = "";
            if (!(dataList.isNull(0))) {
                JSONObject data = dataList.getJSONObject(0);
                message = data.getString("message");
                timestamp = data.getString("timestamp");
                email = data.getString("email");
            }


            if (!mMessageMap.getValue().containsKey(chatID)) {


                List<String> messageInfo = new ArrayList<>();
                messageInfo.add(0, email);
                messageInfo.add(1, message);
                messageInfo.add(2, timestamp);


                mMessageMap.getValue().put(chatID, messageInfo);
                mMessageMap.setValue(mMessageMap.getValue());


            } else {
                List<String> messageInfo = mMessageMap.getValue().get(chatID);
                messageInfo.set(0, email);
                messageInfo.set(1, message);
                messageInfo.set(2, timestamp);
                mMessageMap.getValue().replace(chatID, messageInfo);
                mMessageMap.setValue(mMessageMap.getValue());

            }

        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle CHATID ChatTestViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * Sends a GET to the server requesting conversation data.
     * TODO this is hard coded for the 4 chat rooms. In the future this will require broader implementation. Original down below.
     * @param theJwt a verified jwt object.
     * @param theChatID id to specify chat room.
     */
    public void connectGetUsernames(int theChatID, String theJwt) {

        String url =
                "https://team8-tcss450-app.herokuapp.com/chats/" + theChatID;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleUsernames,
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
     * Sends a GET to the server requesting the list of chatIDs
     * @param theEmail the user email.
     * @param theJwt the Json web token.
     */
    public void connectGetChatIDs(String theEmail, String theJwt) {

        String url =
                "https://team8-tcss450-app.herokuapp.com/chats/chatIDs/" + theEmail;    // TODO change to live data source
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleListOfChatIDs,
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
     * Sends a GET to the server requesting the nickname
     * @param theEmail the user email.
     * @param theJwt the Json web token.
     */
    public void connectNickName(String theEmail, String theJwt) {
        String url =
                "https://team8-tcss450-app.herokuapp.com/chats/nickname/" + theEmail;    // TODO change to live data source
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleNickname,
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

    

    public void connectMessages(Integer theChatID, String theJwt) {

        String url =
                "https://team8-tcss450-app.herokuapp.com/messages/" + theChatID;    // TODO change to live data source
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleMessages,
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



}


//    /**
//     * Received and interprets data sent from Volley.
//     * TODO Currently generates dummy data, update when live data is valid.
//     * @param result A JSONObject generated by Volley with valid data.
//     */
//    private void handleResult(final JSONObject result) {
//        mChatList.setValue(new ArrayList<>());
//        for(int i = 0; i < 4; i++) {    // TODO remove when live data is implemented
//            edu.uw.tcss450.team8tcss450.ui.chat.ChatConversation post = new edu.uw.tcss450.team8tcss450.ui.chat.ChatConversation.Builder(
//                    "My Dearest Friend #" + (i + 1), mTempMessage)
//                    //.addMessage(System.getProperty("line.separator") + "Here's the second part of the message!")  // TODO change to message object pt. 2
//                    .build();
//            if (!mChatList.getValue().contains(post)) {
//                mChatList.getValue().add(post);
//            }
//        }
//        mChatList.setValue(mChatList.getValue());
//    }
//
//    /**
//     * Sends a GET to the server requesting conversation data.
//     * TODO add contactID to request, update link to a valid URL.
//     * @param jwt a verified jwt object.
//     */
//    public void connectGet(String jwt) {
//        String url =
//                "https://team8-tcss450-app.herokuapp.com/auth";    // TODO change to live data source
//        Request request = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                null, //no body for this get request
//                this::handleResult,
//                this::handleError) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                // add headers <key,value>
//                headers.put("Authorization", jwt);
//                return headers;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                10_000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////Instantiate the RequestQueue and add the request to the queue
//        Volley.newRequestQueue(getApplication().getApplicationContext())
//                .add(request);
//}