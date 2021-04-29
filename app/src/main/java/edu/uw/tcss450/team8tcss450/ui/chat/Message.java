package edu.uw.tcss450.team8tcss450.ui.chat;

/**
 * Object class for individual messages. Contains private data with getters.
 * To be contained within the ChatConversation class in a list.
 *
 * @author Marc Perez
 * @date 27 April 2021
 */

import java.io.Serializable;
import java.util.Calendar;

public class Message implements Serializable {
    // Variables //
    private String mName; // Name of sender
    private String mDate; // date message was sent
    private String mTime; // time at given date the message was sent
    private String mMesssage; // full message sent
    // Methods //

    /**
     * Public constructor for messages being received from database. Sets all variables manually.
     * @param name Name of sender.
     * @param message full message sent.
     * @param date date message was SENT.
     * @param time time at given date the message was SENT.
     */
    public Message (String name, String message, String date, String time) {
        mName = name;
        mMesssage = message;
        mDate = date;
        mTime = time;
    }

    /**
     * Public constructor for message being sent from local device. assigns date and time based on
     * machine clock. TODO consider setting time/date server side.
     * @param name Name of sender.
     * @param message full message sent.
     */
    public Message (String name, String message) {
        mName = name;
        mMesssage = message;
        mDate = Calendar.getInstance().getTime().toString();    // TODO specify
        mTime = Calendar.getInstance().getTime().toString();
    }

    public String getName() {
        return mName;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getMesssage() {
        return mMesssage;
    }
}
