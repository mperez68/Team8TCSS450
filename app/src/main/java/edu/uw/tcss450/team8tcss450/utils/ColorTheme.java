package edu.uw.tcss450.team8tcss450.utils;

import android.app.Activity;

import edu.uw.tcss450.team8tcss450.R;

public class ColorTheme {

    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_2 = 1;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.recreate();

    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.Theme_Team8GroupProject);
                break;
            case THEME_2:
                activity.setTheme(R.style.Theme_Team8GroupProjectAlt);
                break;
        }
    }
}
