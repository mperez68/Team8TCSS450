package edu.uw.tcss450.team8tcss450;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
// TODO filler class, replace as needed

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.team8tcss450.model.PushyTokenViewModel;
import edu.uw.tcss450.team8tcss450.utils.ColorTheme;
import me.pushy.sdk.Pushy;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("App Shared Prefs", Context.MODE_PRIVATE);

        String theme = prefs.getString("Shared Prefs Theme", "Default");

        if (theme.equals("Alt")) {
            ColorTheme.setTheme(ColorTheme.THEME_ALT);
        } else {
            ColorTheme.setTheme(ColorTheme.THEME_DEFAULT);
        }

        ColorTheme.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_auth);

        //If it is not already running, start the Pushy listening service
        Pushy.listen(this);

        initiatePushyTokenRequest();
    }

    private void initiatePushyTokenRequest() {
        new ViewModelProvider(this).get(PushyTokenViewModel.class).retrieveToken();
    }
}