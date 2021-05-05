package edu.uw.tcss450.team8tcss450.ui.weather.forecast.days;

import java.util.Arrays;
import java.util.List;

/**
 * Generator for posts of the 10-day forecast
 *
 * @author Brandon Kennedy
 * @version 30 April 2021
 */
public final class WeatherDayPredictionGenerator {

    // the array of the weather-day prediction posts forecasting the next 10 days
    private static final WeatherDayPostInfo[] posts;
    private static final int COUNT = 10;

    // create, upon construction of this generator,
    // a mock-up list of weather day predictions with dummy data
    static {
        posts = new WeatherDayPostInfo[COUNT];
        for (int i = 0; i < posts.length; i++) {
            posts[i] = new WeatherDayPostInfo
                    .WeatherDayInfoBuilder("Day " + String.valueOf(i+1))
                    .addOutlook("Sunny")
                    .addLowTemperature(String.valueOf(20+i))
                    .addHighTemperature(String.valueOf(50+i))
                    .build();
        }
    }

    /**
     * Construct a new weather day prediction generator
     */
    private WeatherDayPredictionGenerator() {
        // remain empty for now
    }

    /**
     * Return the array of weather day prediction posts in an array list
     *
     * @return the array of weather day prediction posts in an array list
     */
    public static List<WeatherDayPostInfo> getWeatherInfoList() {
        return Arrays.asList(posts);
    }

    /**
     * Return a copy of the array of weather day prediction posts
     *
     * @return a copy of the array of weather day prediction posts
     */
    public static WeatherDayPostInfo[] getInfoPosts() {
        return Arrays.copyOf(posts, posts.length);
    }

}
