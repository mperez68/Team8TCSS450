package edu.uw.tcss450.team8tcss450.ui.chat.conversation;

/**
 * Object class for individual messages. Contains private data with getters.
 * To be contained within the ChatConversation class in a list.
 *
 * @author Marc Perez
 * @date 27 April 2021
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import androidx.annotation.Nullable;
import edu.uw.tcss450.team8tcss450.ui.chat.test.ChatTestMessage;

public class Message implements Serializable {

    private int mMessageId;
    /**
     * Name of sender.
     */
    private String mSender;
    /**
     * date and/or time message was sent.
     */
    private String mTimeStamp;
    /**
     * full message sent.
     */
    private String mMessage;

    /**
     * Public constructor for messages being received from database. Sets all variables manually.
     */
    public Message (int messageId, String message, String sender, String timeStamp) {
        mMessageId = messageId;
        mMessage = message;
        mSender = sender;
        mTimeStamp = timeStamp;
    }

    /**
     * Public constructor for message being sent from local device. assigns date and time based on
     * machine clock. TODO consider setting time/date server side.
     * @param name Name of sender.
     * @param message full message sent.
     */
    public Message (String name, String message) {
        mSender = name;
        mMessage = message;
        mTimeStamp = "1/1/1 11:11:11";
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatMessage object.
     * @param cmAsJson the String to be parsed into a ChatMessage Object.
     * @return a ChatMessage Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static ChatTestMessage createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new ChatTestMessage(msg.getInt("messageid"),
                msg.getString("message"),
                msg.getString("email"),
                msg.getString("timestamp"));
    }

    /**
     * Getter function for the name of the sender.
     * @return Name of sender.
     */
    public String getName() {
        return mSender;
    }

    /**
     * Getter function for the date message was sent.
     * @return date message was sent.
     */
    public String getmTimeStamp() {
        return mTimeStamp;
    }

    /**
     * Getter function for the full message sent.
     * @return full message sent.
     */
    public String getMesssage() {
        return mMessage;
    }

    public int getMessageId() {
        return mMessageId;
    }

    /**
     * Provides equality solely based on MessageId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof Message) {
            result = mMessageId == ((Message) other).mMessageId;
        }
        return result;
    }
}
