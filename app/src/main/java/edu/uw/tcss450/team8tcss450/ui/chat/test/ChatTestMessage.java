package edu.uw.tcss450.team8tcss450.ui.chat.test;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Class representing a chat message.
 */
public class ChatTestMessage implements Serializable {

    private final int mMessageId;
    private final String mMessage;
    private final String mSender;
    private final String mTimeStamp;


    /**
     * ChatTestMessage constructor
     *
     * @param theMessageID
     * @param theMessage
     * @param theSender
     * @param theTimeStamp
     */
    public ChatTestMessage(int theMessageID, String theMessage, String theSender, String theTimeStamp) {
        mMessageId = theMessageID;
        mMessage = theMessage;
        mSender = theSender;
        mTimeStamp = theTimeStamp;
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
     * getter for the message
     *
     * @return the Message
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * getter for the sender
     *
     * @return the sender
     */
    public String getSender() {
        return mSender;
    }

    /**
     * getter for the time
     *
     * @return the time
     */
    public String getTimeStamp() {
        return mTimeStamp;
    }

    /**
     * getter for the message ID
     *
     * @return the Message ID
     */
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
        if (other instanceof ChatTestMessage) {
            result = mMessageId == ((ChatTestMessage) other).mMessageId;
        }
        return result;
    }
}
