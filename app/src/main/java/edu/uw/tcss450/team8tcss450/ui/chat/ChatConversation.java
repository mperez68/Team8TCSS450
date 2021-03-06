package edu.uw.tcss450.team8tcss450.ui.chat;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Class to encapsulate a Phish.net Blog Post. Building an Object requires a publish date and title.
 *
 * Optional fields include URL, teaser, and Author.
 *
 *
 * @author Charles Bryan
 * @version 14 September 2018
 *
 * Modified blog post recycler, designed to post a list of chat messages currently open.
 *
 * @author Marc Perez
 * @version 27 April 2021
 */
public class ChatConversation implements Serializable {
    /**
     * name of the contact.
     */
    private final String mContact;

    /**
     * email of the contact.
     */
    private final String mEmail;

    /**
     * Message in this send.
     */
    private LinkedList<String> mMessage;   // TODO change to message object pt. 1

    /**
     * date and/or time message was sent.
     */
    private String mTimeStamp;

    /**
     * Chat ID
     */
    private int myChatID;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     * modified by Marc Perez
     */
    public static class Builder {
        private final String mContact;
        private final String mEmail;
        private final String mTimeStamp;
        private LinkedList<String> mMessage = new LinkedList<>();
        private int myChatID;

        /**
         * Constructs a new Builder.
         *
         * @param contact
         * @param message
         */
        public Builder(String contact, String email, String message, String timeStamp, int chatID) {
            this.mContact = contact;
            this.mEmail = email;
            this.mMessage.add(message);
            this.myChatID = chatID;
            this.mTimeStamp = timeStamp;
        }

        public ChatConversation build() {
            return new ChatConversation(this);
        }

    }

    private ChatConversation(final Builder builder) {
        this.mContact = builder.mContact;
        this.mEmail = builder.mEmail;
        this.mMessage = builder.mMessage;
        this.myChatID = builder.myChatID;
        this.mTimeStamp = builder.mTimeStamp;
    }

    /**
     * Getter function for the name of the contact.
     * @return the name of the contact.
     */
    public String getmContact() {
        return mContact;
    }

    /**
     * Getter function for the email of the contact.
     * @return the email of the contact.
     */
    public String getmEmail() {
        return mEmail;
    }

    /**
     * Getter function for the timestamp
     * @return the timestamp
     */
    public String getmTimeStamp() {
        return mTimeStamp;
    }

    /**
     * Getter function for the message in this send.
     * @return Message in this send.
     */
    public LinkedList getmMessage() {
        return (LinkedList) mMessage.clone();
    }

    public int getMyChatID() {
        return myChatID;
    }
}
