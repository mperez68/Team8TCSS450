package edu.uw.tcss450.team8tcss450.ui.contacts;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {
    private String myName;
    private String myUserName;
    private String myEmail;
    private boolean myOnline;

    public Contact(String theName, String theUserName, String theEmail, boolean theOnline)  {
        myName = theName;
        myUserName = theUserName;
        myEmail = theEmail;
        myOnline = theOnline;
    }

    public String getUserName() {
        return myUserName;
    }

    public String getEmail() {
        return myEmail;
    }

    public String getName() {
        return myName;
    }

    public boolean isOnline() {
        return myOnline;
    }


}
