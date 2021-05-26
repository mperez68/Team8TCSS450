package edu.uw.tcss450.team8tcss450.utils;

import android.app.Activity;

import edu.uw.tcss450.team8tcss450.R;

public class ColorTheme {

    private static int mTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_ALT = 1;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        mTheme = theme;
        activity.recreate();

    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (mTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.Theme_Team8GroupProject);
                break;
            case THEME_ALT:
                activity.setTheme(R.style.Theme_Team8GroupProjectAlt);
                break;
        }
    }

    /**
     * Set theme without recreating activity.
     */
    public static void setTheme(int theme) {
        mTheme = theme;
    }
}
