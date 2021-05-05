package edu.uw.tcss450.team8tcss450.ui.weather;

import java.io.Serializable;
import java.util.Date;

/**
 * Class to encapsulate a node of current information and
 * statistics of the weather conditions of a zip code
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public class WeatherCurrentInfo implements Serializable {

    private Date mDate;
    private String mZipcode;
    private String mOutlook;
    private String mCurrentTemperature;
    private String mHighTemperature;
    private String mLowTemperature;
    private String mHumidity;
    private String mWindSpeed;
    private String mWindDirection;

    /**
     * Construct a new node of current information and
     * statistics of the weather conditions of a zip code
     *
     * @param builder the builder class is used to construct the new current weather display node
     */
    private WeatherCurrentInfo(final WeatherCurrentInfoBuilder builder) {
        this.mDate = builder.mDate;
        this.mZipcode = builder.mZipcode;
        this.mOutlook = builder.mOutlook;
        this.mCurrentTemperature = builder.mCurrentTemperature;
        this.mHighTemperature = builder.mHighTemperature;
        this.mLowTemperature = builder.mLowTemperature;
        this.mHumidity = builder.mHumidity;
        this.mWindSpeed = builder.mWindSpeed;
        this.mWindDirection = builder.mWindDirection;
    }

    /**
     * Return the date of the current weather display
     *
     * @return the date of the current weather display
     */
    public String getDate() {
        return this.mDate.toString();
    }

    /**
     * Return the zipcode of the current weather display
     *
     * @return the zipcode of the current weather display
     */
    public String getZipcode() {
        return this.mZipcode;
    }

    /**
     * Return the outlook label of the current weather display
     *
     * @return the outlook label of the current weather display
     */
    public String getOutlook() {
        return this.mOutlook;
    }

    /**
     * Return the current temperature of the current weather display
     *
     * @return the current temperature of the current weather display
     */
    public String getCurrentTemperature() {
        return this.mCurrentTemperature;
    }

    /**
     * Return the low temperature of the current weather display
     *
     * @return the low temperature of the current weather display
     */
    public String getLowTemperature() {
        return this.mLowTemperature;
    }

    /**
     * Return the high temperature of the current weather display
     *
     * @return the high temperature of the current weather display
     */
    public String getHighTemperature() {
        return this.mHighTemperature;
    }

    /**
     * Return the humidity of the current weather display
     *
     * @return the humidity of the current weather display
     */
    public String getHumidity() {
        return this.mHumidity;
    }

    /**
     * Return the wind speed of the current weather display
     *
     * @return the wind speed of the current weather display
     */
    public String getWindSpeed() {
        return this.mWindSpeed;
    }

    /**
     * Return the wind direction of the current weather display
     *
     * @return the wind direction of the current weather display
     */
    public String getWindDirection() {
        return this.mWindDirection;
    }

    /**
     * Helper class for building credientials of the current weather conditions display
     *
     * @author Brandon Kennedy
     * @version 30 April 2021
     */
    public static final class WeatherCurrentInfoBuilder {

        private Date mDate;
        private String mZipcode;
        private String mOutlook;
        private String mCurrentTemperature;
        private String mHighTemperature;
        private String mLowTemperature;
        private String mHumidity;
        private String mWindSpeed;
        private String mWindDirection;

        /**
         * Constructs a new Weather Info builder.
         *
         * @param date the date for the current weather display
         * @param zipcode the zip code in which the current weather display is for
         */
        public WeatherCurrentInfoBuilder(Date date, String zipcode) {
            this.mDate = date;
            this.mZipcode = zipcode;
        }

        /**
         * Add an outlook label that will be used to determine the outlook graphic
         * for display on the current weather
         *
         * @param outlook the name of the outlook graphic
         * @return the builder of this current weather display node
         */
        public WeatherCurrentInfoBuilder addOutlook(final String outlook) {
            this.mOutlook = outlook;
            return this;
        }

        /**
         * Add a temperature reading for display on the current weather
         *
         * @param temperature the reading value of the current temperature
         * @return the builder of this current weather display node
         */
        public WeatherCurrentInfoBuilder addCurrentTemp(final String temperature) {
            this.mCurrentTemperature = temperature;
            return this;
        }

        /**
         * Add both a low temperature reading and a high temperature reading
         * for display on the current weather
         *
         * @param highTemperature the reading value of the high temperature
         * @param lowTemperature the reading value of the low temperature
         * @return the builder of this current weather display node
         */
        public WeatherCurrentInfoBuilder addHighAndLowTemp(final String highTemperature,
                                                           final String lowTemperature) {
            this.mHighTemperature = highTemperature;
            this.mLowTemperature = lowTemperature;
            return this;
        }

        /**
         * Add a humidity reading for display on the current weather
         *
         * @param humidity the reading value of the current humidity
         * @return the builder of this current weather display node
         */
        public WeatherCurrentInfoBuilder addHumidity(final String humidity) {
            this.mHumidity = humidity;
            return this;
        }

        /**
         * Add both the wind speed reading and the wind direction reading
         * for display on the current weather
         *
         * @param windSpeed the reading value of the current wind speed
         * @param windDirection the reading value of the current wind direction
         * @return the builder of this current weather display node
         */
        public WeatherCurrentInfoBuilder addWindSpeedAndDirection(final String windSpeed,
                                                                  final String windDirection) {
            this.mWindSpeed = windSpeed;
            this.mWindDirection = windDirection;
            return this;
        }

        /**
         * Create and return a newly constructed object of this inner class.
         *
         * @return a new WeatherCurrentInfo object
         */
        public WeatherCurrentInfo build() {
            return new WeatherCurrentInfo(this);
        }

    }

}
