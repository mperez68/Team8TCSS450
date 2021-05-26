package edu.uw.tcss450.team8tcss450.ui.weather;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Class to encapsulate a post of information and
 * statistics of the weather conditions of the current day
 *
 * @author Brandon Kennedy
 * @version 21 May 2021
 */
public class WeatherCurrentInfo implements Serializable {

    private String mCity;
    private String mTemperature;
    private String mOutlook;
    private Bitmap mOutlookIcon;
    private String mHumidity;
    private String mWindSpeed;
    private String mWindDirection;
    private String mVisibility;

    /**
     * Construct a new instance of information and statistics
     * of the current weather conditions
     *
     * @param builder the builder class is used to construct the new current weather instance
     */
    private WeatherCurrentInfo(final WeatherCurrentInfoBuilder builder) {
        this.mCity = builder.mCity;
        this.mTemperature = builder.mTemperature;
        this.mOutlook = builder.mOutlook;
        this.mHumidity = builder.mHumidity;
        this.mWindSpeed = builder.mWindSpeed;
        this.mWindDirection = builder.mWindDirection;
        this.mVisibility = builder.mVisibility;
    }

    /**
     * Return the city of the current weather instance
     *
     * @return the city of the current weather instance
     */
    public String getCity() {
        return this.mCity;
    }

    /**
     * Return the temperature of the current weather instance
     *
     * @return the temperature of the current weather instance
     */
    public String getTemperature() {
        return this.mTemperature;
    }

    /**
     * Return the outlook of the current weather instance
     *
     * @return the outlook of the current weather instance
     */
    public String getOutlook() {
        return this.mOutlook;
    }

    /**
     * Return the outlook icon of the current weather instance
     *
     * @return the outlook icon of the current weather instance
     */
    public Bitmap getOutlookIcon() {
        return this.mOutlookIcon;
    }

    /**
     * Return the humidity of the current weather instance
     *
     * @return the humidity of the current weather instance
     */
    public String getHumidity() {
        return this.mHumidity;
    }

    /**
     * Return the wind speed of the current weather instance
     *
     * @return the wind speed of the current weather instance
     */
    public String getWindSpeed() {
        return this.mWindSpeed;
    }

    /**
     * Return the wind direction of the current weather instance
     *
     * @return the wind direction of the current weather instance
     */
    public String getWindDirection() {
        return this.mWindDirection;
    }

    /**
     * Return the visibility of the current weather instance
     *
     * @return the visibility of the current weather instance
     */
    public String getVisibility() {
        return this.mVisibility;
    }

    /**
     * Set the outlook icon of the current weather instance
     *
     * @param outlookIcon the outlook icon being stored with the current weather instance
     */
    public void setOutlookIcon(final Bitmap outlookIcon) {
        this.mOutlookIcon = outlookIcon;
    }

    /**
     * Helper class for building credentials of the current weather instance
     *
     * @author Brandon Kennedy
     * @version 21 May 2021
     */
    public static class WeatherCurrentInfoBuilder {

        private String mCity;
        private String mTemperature;
        private String mOutlook;
        private Bitmap mOutlookIcon;
        private String mHumidity;
        private String mWindSpeed;
        private String mWindDirection;
        private String mVisibility;

        /**
         * Construct a new builder for a current weather display
         *
         * @param city the city for this current weather display
         */
        public WeatherCurrentInfoBuilder(String city) {
            this.mCity = city;
        }

        /**
         * Add an outlook label for display on the current weather display
         *
         * @param outlook the name of the outlook graphic
         * @return the builder of this current weather info
         */
        public WeatherCurrentInfoBuilder addOutlook(final String outlook) {
            this.mOutlook = outlook;
            return this;
        }

        /**
         * Add an outlook image icon for display on the current weather display
         *
         * @param outlookIcon the image of the outlook icon
         * @return the builder of this current weather info
         */
        public WeatherCurrentInfoBuilder addOutlookIcon(final Bitmap outlookIcon) {
            this.mOutlookIcon = outlookIcon;
            return this;
        }

        /**
         * Add a high temperature reading for display on the current weather display
         *
         * @param temperature the reading value of the current temperature
         * @return the builder of this current weather info
         */
        public WeatherCurrentInfoBuilder addTemperature(final String temperature) {
            this.mTemperature = temperature;
            return this;
        }

        /**
         * Add a humidity reading for display on the current weather display
         *
         * @param humidity the reading value of the current humidity
         * @return the builder of this current weather info
         */
        public WeatherCurrentInfoBuilder addHumidity(final String humidity) {
            this.mHumidity = humidity;
            return this;
        }

        /**
         * Add a wind speed and direction reading for display on the current weather display
         *
         * @param windSpeed the reading value of the current wind speed
         * @param windDirection the reading value of the current wind direction
         *
         * @return the builder of this current weather info
         */
        public WeatherCurrentInfoBuilder addWind(final String windSpeed,
                                                 final String windDirection) {
            this.mWindSpeed = windSpeed;
            this.mWindDirection = windDirection;
            return this;
        }

        /**
         * Add a visibility reading for display on the current weather display
         *
         * @param visibility the reading value of the current visibility
         * @return the builder of this current weather info
         */
        public WeatherCurrentInfoBuilder addVisibility(final String visibility) {
            this.mVisibility = visibility;
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
