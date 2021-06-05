package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

import edu.uw.tcss450.team8tcss450.ui.weather.forecast.days.WeatherDayPostInfo;

/**
 * Class to encapsulate a post of information and
 * statistics of forecasted weather conditions on
 * a specific hour in a 24-hour forecast.
 *
 * @author Brandon Kennedy
 * @version 4 June 2021
 */
public class WeatherHourPostInfo implements Serializable {

    private final String mDate;
    private final String mTime;
    private final String mOutlook;
    private final Integer mOutlookIconResId;
    private final String mTemperature;

    /**
     * Construct a new post of information and statistics
     * of forecasted weather conditions of a specific hour
     *
     * @param builder the builder class is used to construct the new weather hour post
     */
    private WeatherHourPostInfo(final WeatherHourInfoBuilder builder) {
        this.mDate = builder.mDate;
        this.mTime = builder.mTime;
        this.mOutlook = builder.mOutlook;
        this.mOutlookIconResId = builder.mOutlookIconResId;
        this.mTemperature = builder.mTemperature;
    }

    /**
     * Return the date of the weather hour post
     *
     * @return the date of the weather hour post
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Return the hour of the weather hour post
     *
     * @return the hour of the weather hour post
     */
    public String getTime() {
        return mTime;
    }

    /**
     * Return the outlook label of the weather hour post
     *
     * @return the outlook label of the weather hour post
     */
    public String getOutlook() {
        return mOutlook;
    }

    /**
     * Return the outlook icon resource id of the weather hour post
     *
     * @return the outlook icon resource id of the weather hour post
     */
    public Integer getOutlookIconResId() {
        return mOutlookIconResId;
    }

    /**
     * Return the temperature of the weather hour post
     *
     * @return the temperature of the weather hour post
     */
    public String getTemperature() {
        return mTemperature;
    }

    /**
     * Helper class for building credentials of the weather forecast hour post
     *
     * @author Brandon Kennedy
     * @version 4 June 2021
     */
    public static class WeatherHourInfoBuilder {
        private final String mDate;
        private final String mTime;
        private String mOutlook;
        private Integer mOutlookIconResId;
        private String mTemperature;

        /**
         * Construct a new builder for a weather forecast hour post
         *
         * @param date the date for this post's weather prediction
         * @param time the time for this post's weather prediction
         */
        public WeatherHourInfoBuilder(String date, String time) {
            this.mDate = date;
            this.mTime = time;
        }

        /**
         * Add an outlook label for display on the weather forecast hour post
         *
         * @param outlook the name of the outlook graphic
         * @return the builder of this weather forecast hour post
         */
        public WeatherHourInfoBuilder addOutlook(final String outlook) {
            this.mOutlook = outlook;
            return this;
        }

        /**
         * Add an outlook icon resource id for display on the weather forecast hour post
         *
         * @param outlookIconResId the outlook icon resource id
         * @return the builder of this weather forecast hour post
         */
         public WeatherHourInfoBuilder addOutlookIconResId(final Integer outlookIconResId) {
            this.mOutlookIconResId = outlookIconResId;
            return this;
        }

        /**
         * Add a temperature reading for display on the weather forecast hour post
         *
         * @param temperature the reading value of the temperature
         * @return the builder of this weather forecast hour post
         */
        public WeatherHourInfoBuilder addTemperature(final String temperature) {
            this.mTemperature = temperature;
            return this;
        }

        /**
         * Create and return a newly constructed object of this inner class.
         *
         * @return a new WeatherHourPostInfo object
         */
        public WeatherHourPostInfo build() {
            return new WeatherHourPostInfo(this);
        }
    }

}
