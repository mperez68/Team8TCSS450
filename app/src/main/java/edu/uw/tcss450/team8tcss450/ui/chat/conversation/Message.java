package edu.uw.tcss450.team8tcss450.ui.chat.conversation;

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
    /**
     * Name of sender.
     */
    private String mName;
    /**
     * date message was sent.
     */
    private String mDate;
    /**
     *  time at given date the message was sent.
     */
    private String mTime;
    /**
     * full message sent.
     */
    private String mMesssage;

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
        mDate = "5/7"; //Calendar.getInstance().getTime().toString();    // TODO specify
        mTime = "5/7"; //Calendar.getInstance().getTime().toString();
    }

    /**
     * Getter function for the name of the sender.
     * @return Name of sender.
     */
    public String getName() {
        return mName;
    }

    /**
     * Getter function for the date message was sent.
     * @return date message was sent.
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Getter function for the time at given date the message was sent.
     * @return time at given date the message was sent.
     */
    public String getTime() {
        return mTime;
    }

    /**
     * Getter function for the full message sent.
     * @return full message sent.
     */
    public String getMesssage() {
        return mMesssage;
    }
}
