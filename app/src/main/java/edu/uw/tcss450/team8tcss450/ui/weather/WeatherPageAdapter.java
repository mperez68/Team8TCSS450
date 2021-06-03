package edu.uw.tcss450.team8tcss450.ui.weather;
;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team8tcss450.ui.weather.current.WeatherCurrentFragment;
import edu.uw.tcss450.team8tcss450.ui.weather.forecast.days.WeatherDayPredictionListFragment;
import edu.uw.tcss450.team8tcss450.ui.weather.forecast.hours.WeatherHourPredictionListFragment;
import edu.uw.tcss450.team8tcss450.ui.weather.map.WeatherMapFragment;

/**
 * Creates a fragment state pager adapter that managers the
 * fragment pages on the WeatherMainFragment's view pager.
 *
 * @author Brandon Kennedy
 * @version 2 June 2021
 */
public class WeatherPageAdapter extends FragmentStatePagerAdapter {

    /**
     * Constructor for the WeatherPageAdapter
     *
     * @param manager the manager for the pages of the view pager the adapter is connected to
     */
    public WeatherPageAdapter(FragmentManager manager) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.i("WeatherPageAdapter", "Clicked " + position + ". Returned new WeatherCurrentFragment");
                return new WeatherCurrentFragment();
            case 1:
                Log.i("WeatherPageAdapter", "Clicked " + position + ". Returned new WeatherHourPredictionListFragment");
                return new WeatherHourPredictionListFragment();
            case 2:
                Log.i("WeatherPageAdapter", "Clicked " + position + ". Returned new WeatherDayPredictionListFragment");
                return new WeatherDayPredictionListFragment();
            case 3:
                Log.i("WeatherPageAdapter", "Clicked " + position + ". Returned new WeatherMapFragment");
                return new WeatherMapFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
