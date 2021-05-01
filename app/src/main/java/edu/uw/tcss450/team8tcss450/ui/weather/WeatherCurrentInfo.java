package edu.uw.tcss450.team8tcss450.ui.weather;

import java.io.Serializable;
import java.util.Date;

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

    WeatherCurrentInfo(final WeatherCurrentInfo.WeatherCurrentInfoBuilder builder) {
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

    public String getDate() {
        return this.mDate.toString();
    }

    public String getZipcode() {
        return this.mZipcode;
    }

    public String getOutlook() {
        return this.mOutlook;
    }

    public String getCurrentTemperature() {
        return this.mCurrentTemperature;
    }

    public String getLowTemperature() {
        return this.mLowTemperature;
    }

    public String getHighTemperature() {
        return this.mHighTemperature;
    }

    public String getHumidity() {
        return this.mHumidity;
    }

    public String getWindSpeed() {
        return this.mWindSpeed;
    }

    public String getWindDirection() {
        return this.mWindDirection;
    }

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

        public WeatherCurrentInfoBuilder(Date date, String zipcode) {
            this.mDate = date;
            this.mZipcode = zipcode;
        }

        public WeatherCurrentInfoBuilder addOutlook(final String outlook) {
            this.mOutlook = outlook;
            return this;
        }

        public WeatherCurrentInfoBuilder addCurrentTemp(final String temperature) {
            this.mCurrentTemperature = temperature;
            return this;
        }

        public WeatherCurrentInfoBuilder addHighAndLowTemp(final String highTemperature,
                                                           final String lowTemperature) {
            this.mHighTemperature = highTemperature;
            this.mLowTemperature = lowTemperature;
            return this;
        }

        public WeatherCurrentInfoBuilder addHumidity(final String humidity) {
            this.mHumidity = humidity;
            return this;
        }

        public WeatherCurrentInfoBuilder addWindSpeedAndDirection(final String windSpeed,
                                                                  final String windDirection) {
            this.mWindSpeed = windSpeed;
            this.mWindDirection = windDirection;
            return this;
        }

        public WeatherCurrentInfo build() {
            return new WeatherCurrentInfo(this);
        }

    }

}
