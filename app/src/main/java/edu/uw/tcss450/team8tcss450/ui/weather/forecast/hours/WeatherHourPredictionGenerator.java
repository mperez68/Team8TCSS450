package edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours;

import java.util.Arrays;
import java.util.List;

/**
 * Generator for posts of the 24-hour forecast
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public final class WeatherHourPredictionGenerator {

    // the array of the weather-hour prediction posts forecasting the next 24 hours
    private static final WeatherHourPostInfo[] weatherHourPosts;
    public static final int COUNT = 24;

    // create, upon construction of this generator,
    // a mock-up list of weather hour predictions with dummy data
    static {
        weatherHourPosts = new WeatherHourPostInfo[COUNT];
        for (int i = 0; i < weatherHourPosts.length; i++) {
            weatherHourPosts[i] = new WeatherHourPostInfo
                    .WeatherHourInfoBuilder("Today", "Hour " + String.valueOf(i))
                    .addOutlook("Sunny")
                    .addTemperature(String.valueOf(50+i))
                    .build();
        }

    }

    /**
     * Return the array of weather hour prediction posts in an array list
     *
     * @return the array of weather hour prediction posts in an array list
     */
    public static List<WeatherHourPostInfo> getPostList() {
        return Arrays.asList(weatherHourPosts);
    }

    /**
     * Return a copy of the array of weather hour prediction posts
     *
     * @return a copy of the array of weather hour prediction posts
     */
    public static WeatherHourPostInfo[] getPosts() {
        return Arrays.copyOf(weatherHourPosts, weatherHourPosts.length);
    }

    /**
     * Construct a new weather hour prediction generator
     */
    private WeatherHourPredictionGenerator() { }

}
