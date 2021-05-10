package edu.uw.tcss450.team8tcss450;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.auth0.android.jwt.JWT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        JWT jwt = new JWT(args.getJwt());

        // Check to see if the web token is still valid or not. To make a JWT expire after a
        // longer or shorter time period, change the expiration time when the JWT is
        // created on the web service.
        if(!jwt.isExpired(UserInfoViewModel.mLeeway)) {
            new ViewModelProvider(
                    this,
                    new UserInfoViewModel.UserInfoViewModelFactory(jwt))
                    .get(UserInfoViewModel.class);
        } else {
            //In production code, add in your own error handling/flow for when the JWT is expired
            throw new IllegalStateException("JWT is expired!");
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_contacts,
            R.id.navigation_chat, R.id.navigation_weather)
            .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_main_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_main_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // create toolbar
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //TODO open a settings fragment


            Log.d("SETTINGS", "Clicked");
            return true;
        }
        else if (id == R.id.action_sign_out) {
            // if sign out is clicked, return to log in page
            Intent auth = new Intent(this, AuthActivity.class);
            startActivity(auth);
            this.finish();

            Log.d("SIGN OUT", "Clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

