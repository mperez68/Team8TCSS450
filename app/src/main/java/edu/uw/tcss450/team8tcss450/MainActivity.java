package edu.uw.tcss450.team8tcss450;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.auth0.android.jwt.JWT;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450.team8tcss450.databinding.ActivityMainBinding;
import edu.uw.tcss450.team8tcss450.model.NewMessageCountViewModel;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.services.PushReceiver;
import edu.uw.tcss450.team8tcss450.ui.chat.test.ChatTestMessage;
import edu.uw.tcss450.team8tcss450.ui.chat.test.ChatTestViewModel;


public class MainActivity extends AppCompatActivity {

// PUSHY MESSAGING added from lab 5
    private ActivityMainBinding binding;
    private MainPushMessageReceiver mPushMessageReceiver;
    private NewMessageCountViewModel mNewMessageModel;
//    end

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

        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class); //pushy messaging

        //TODO change this accordingly for messages
//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            if (destination.getId() == R.id.navigation_chat) {
//                //When the user navigates to the chats page, reset the new message count.
//                //This will need some extra logic for your project as it should have
//                //multiple chat rooms.
//                mNewMessageModel.reset();
//            }
//        });

        mNewMessageModel.addMessageCountObserver(this, count -> {
            BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.navigation_chat);
            badge.setMaxCharacterCount(2);
            if (count > 0) {
                //new messages! update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                //user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_main_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPushMessageReceiver == null) {
            mPushMessageReceiver = new MainPushMessageReceiver();
        }
        IntentFilter iFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        registerReceiver(mPushMessageReceiver, iFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null){
            unregisterReceiver(mPushMessageReceiver);
        }
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


    /**
     * A BroadcastReceiver that listens for messages sent from PushReceiver
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {

        private ChatTestViewModel mModel =
                new ViewModelProvider(MainActivity.this)
                        .get(ChatTestViewModel.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            NavController nc =
                    Navigation.findNavController(
                            MainActivity.this, R.id.nav_main_host_fragment);
            NavDestination nd = nc.getCurrentDestination();

            if (intent.hasExtra("chatMessage")) {

                ChatTestMessage cm = (ChatTestMessage) intent.getSerializableExtra("chatMessage");

                //If the user is not on the chat screen, update the
                // NewMessageCountView Model
                if (nd.getId() != R.id.navigation_chat) {
                    mNewMessageModel.increment();
                }
                //Inform the view model holding chatroom messages of the new
                //message.
                mModel.addMessage(intent.getIntExtra("chatid", -1), cm);
            }
        }
    }
}

