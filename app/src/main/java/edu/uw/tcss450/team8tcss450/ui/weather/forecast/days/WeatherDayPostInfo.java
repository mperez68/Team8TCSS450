package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

/**
 * Class to encapsulate a post of information and
 * statistics of forecasted weather conditions on
 * a specific day in a 10-day forecast.
 *
 * @author Brandon Kennedy
 * @version 4 June 2021
 */
public class WeatherDayPostInfo implements Serializable {

    private final String mDate;
    private final String mOutlook;
    private final Integer mOutlookIconResId;
    private final String mHighTemperature;
    private final String mLowTemperature;

    /**
     * Construct a new post of information and statistics
     * of forecasted weather conditions of a specific day
     *
     * @param builder the builder class is used to construct the new weather day post
     */
    private WeatherDayPostInfo(final WeatherDayInfoBuilder builder) {
        this.mDate = builder.mDate;
        this.mOutlook = builder.mOutlook;
        this.mOutlookIconResId = builder.mOutlookIconResId;
        this.mHighTemperature = builder.mHighTemperature;
        this.mLowTemperature = builder.mLowTemperature;
    }

    /**
     * Return the date of the weather day post
     *
     * @return the date of the weather day post
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Return the outlook label of the weather day post
     *
     * @return the outlook label of the weather day post
     */
    public String getOutlook() {
        return mOutlook;
    }

    /**
     * Return the outlook icon resource id of the weather day post
     * @return the outlook icon resource id of the weather day post
     */
    public Integer getOutlookIconResId() {
        return mOutlookIconResId;
    }

    /**
     * Return the high temperature reading of the weather day post
     *
     * @return the high temperature reading of the weather day post
     */
    public String getHighTemperature() {
        return mHighTemperature;
    }

    /**
     * Return the low temperature reading of the weather day post
     *
     * @return the low temperature reading of the weather day post
     */
    public String getLowTemperature() { return mLowTemperature; }

    /**
     * Helper class for building credentials of the weather forecast day post
     *
     * @author Brandon Kennedy
     * @version 4 June 2021
     */
    public static class WeatherDayInfoBuilder {
        private final String mDate;
        private String mOutlook;
        private Integer mOutlookIconResId;
        private String mHighTemperature;
        private String mLowTemperature;

        /**
         * Construct a new builder for a weather forecast day post
         *
         * @param date the date for this post's weather prediction
         */
        public WeatherDayInfoBuilder(String date) {
            this.mDate = date;
        }

        /**
         * Add an outlook label for display on the weather forecast day post
         *
         * @param outlook the name of the outlook graphic
         * @return the builder of this weather forecast day post
         */
        public WeatherDayInfoBuilder addOutlook(final String outlook) {
            this.mOutlook = outlook;
            return this;
        }

        /**
         * Add an outlook image resource id for display on the weather forecast day post
         * @param outlookIconResId the outlook image resource id
         * @return the builder of this weather forecast day post
         */
         public WeatherDayInfoBuilder addOutlookIconResId(final Integer outlookIconResId) {
            this.mOutlookIconResId = outlookIconResId;
            return this;
        }

        /**
         * Add a high temperature reading for display on the weather forecast day post
         *
         * @param highTemperature the reading value of the high temperature
         * @return the builder of this weather forecast day post
         */
        public WeatherDayInfoBuilder addHighTemperature(final String highTemperature) {
            this.mHighTemperature = highTemperature;
            return this;
        }

        /**
         * Add a low temperature reading for display on the weather forecast day post
         *
         * @param lowTemperature  the reading value of the low temperature
         * @return the builder of this weather forecast day post
         */
        public WeatherDayInfoBuilder addLowTemperature(final String lowTemperature) {
            this.mLowTemperature = lowTemperature;
            return this;
        }

        /**
         * Create and return a newly constructed object of this inner class.
         *
         * @return a new WeatherDayPostInfo object
         */
        public WeatherDayPostInfo build() {
            return new WeatherDayPostInfo(this);
        }
    }

}