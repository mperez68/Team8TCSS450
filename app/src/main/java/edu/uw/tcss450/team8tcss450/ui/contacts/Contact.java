package edu.uw.tcss450.team8tcss450.ui.contacts;

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

public class Contact implements Serializable {

    private String myName;
    private String myUserName;
    private String myEmail;

    /**
      * Helper class for building Credentials.
      *
      * @author Charles Bryan
      * modified by Nico Roberts
      */
    public static class Builder {
        private String myName;
        private String myUserName;
        private String myEmail;

        /**
         * Constructs a new Builder.
         *
         * @param theName
         * @param theUserName
         * @param theEmail
         */
        public Builder(String theName, String theUserName, String theEmail) {
            this.myName = theName;
            this.myUserName = theUserName;
            this.myEmail = theEmail;
        }

        /**
          * Dummy method not used anywhere yet.
          *
          * @param theVal
          * @return the Builder of this BlogPost
          */
        public Builder addName(final String theVal) {   // TODO implement
            myName = myName + " " + theVal;
            return this;
        }

        /**
         *Dummy method not used anywhere yet.
         *
         * @param val
         * @return the Builder of this BlogPost
         */
        public Builder addUserName(final String val) {   // TODO implement
            myUserName = myUserName + " " + val;
            return this;
        }

        /**
         *Dummy method not used anywhere yet.
         *
         * @param val
         * @return the Builder of this BlogPost
         */
        public Builder addEmail(final String val) {   // TODO implement
            myEmail = myEmail + " " + val;
            return this;
        }

        /**
         *Constructor to build the new contact.
         */
        public Contact build() {

            return new Contact(this);
        }

    }

    /**
     *Constructor for contact that takes in a builder object.
     *
     * @param builder object to build a contact.
     */
    public Contact(final Builder builder)  {
        this.myName = builder.myName;
        this.myUserName = builder.myUserName;
        this.myEmail = builder.myEmail;

    }

    /**
     *Getter for username.
     *
     * @return myUsername
     */
    public String getUserName() {
        return myUserName;
    }

    /**
     *Getter for myEmail.
     *
     * @return myUsername
     */
    public String getEmail() {
        return myEmail;
    }

    /**
     *Getter for myName.
     *
     * @return myName
     */
    public String getName() {
        return myName;
    }



}

