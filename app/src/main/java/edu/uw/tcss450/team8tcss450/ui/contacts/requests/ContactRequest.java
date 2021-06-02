package edu.uw.tcss450.team8tcss450.ui.contacts.requests;

import java.io.Serializable;

/**
 * Class to encapsulate a Phish.net Blog Post. Building an Object requires a publish date and title.
 *
 * Optional fields include URL, teaser, and Author.
 *
 *
 * @author Charles Bryan
 * @version 14 September 2018
 *
 *
 * Creates a contact to use for the recycler view for the contacts list.
 *
 * modified by Nicolas Roberts
 * @version 5 1 2021
 */

public class ContactRequest implements Serializable {

    private String mEmail;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     * modified by Nico Roberts
     */
    public static class Builder {
        private String mEmail;

        /**
         * Constructs a new Builder.
         *
         * @param theEmail
         */
        public Builder(String theEmail) {
            this.mEmail = theEmail;
        }

        /**
         *Constructor to build the new contact.
         */
        public ContactRequest build() {

            return new ContactRequest(this);
        }

    }

    /**
     *Constructor for contact that takes in a builder object.
     *
     * @param builder object to build a contact.
     */
    public ContactRequest(final Builder builder)  {
        this.mEmail = builder.mEmail;
    }

    /**
     *Getter for myEmail.
     *
     * @return mEmail
     */
    public String getEmail() {
        return mEmail;
    }
}

