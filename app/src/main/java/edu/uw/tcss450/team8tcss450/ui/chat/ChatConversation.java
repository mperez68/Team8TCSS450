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
     * Message in this send.
     */
    private LinkedList<String> mMessage;   // TODO change to message object pt. 1

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     * modified by Marc Perez
     */
    public static class Builder {
        private final String mContact;
        private LinkedList<String> mMessage = new LinkedList<>(); // TODO change to message objects pt. 1

        /**
         * Constructs a new Builder.
         *
         * @param contact
         * @param message
         */
        public Builder(String contact, String message) {
            this.mContact = contact;
            this.mMessage.add(message);
        }

        public ChatConversation build() {
            return new ChatConversation(this);
        }

    }

    private ChatConversation(final Builder builder) {
        this.mContact = builder.mContact;
        this.mMessage = builder.mMessage;
    }

    /**
     * Getter function for the name of the contact.
     * @return the name of the contact.
     */
    public String getmContact() {
        return mContact;
    }

    /**
     * Getter function for the message in this send.
     * @return Message in this send.
     */
    public LinkedList getmMessage() {
        return (LinkedList) mMessage.clone();
    }
}
