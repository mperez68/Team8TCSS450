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

    private final String mContact;
    private LinkedList<String> mMessage;   // TODO change to message object pt. 1

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     * modified by Marc Perez
     */
    public static class Builder {
        private final String mContact;
        private LinkedList<String> mMessage = new LinkedList<String>(); // TODO change to message objects pt. 1

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

        /**
         *
         * @param val
         * @return the Builder of this BlogPost
         */
        public Builder addMessage(final String val) {   // TODO implement
            this.mMessage.add(val); // TODO change to message object pt. 1
            return this;
        }

        public ChatConversation build() {
            return new ChatConversation(this);
        }

    }

    private ChatConversation(final Builder builder) {
        this.mContact = builder.mContact;
        this.mMessage = builder.mMessage;
    }

    public String getmContact() {
        return mContact;
    }

    public LinkedList getmMessage() {
        return (LinkedList) mMessage.clone();
    }
}
