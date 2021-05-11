package edu.uw.tcss450.team8tcss450;

import android.os.Bundle;
// TODO filler class, replace as needed

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.team8tcss450.model.PushyTokenViewModel;
import me.pushy.sdk.Pushy;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //If it is not already running, start the Pushy listening service
        Pushy.listen(this);

        initiatePushyTokenRequest();
    }

    private void initiatePushyTokenRequest() {
        new ViewModelProvider(this).get(PushyTokenViewModel.class).retrieveToken();
    }
}