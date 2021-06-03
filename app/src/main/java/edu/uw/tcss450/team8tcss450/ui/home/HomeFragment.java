package edu.uw.tcss450.team8tcss450.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentHomeBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactListTabViewModel;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

//    private HomeViewModel mHomeViewModel;
//    private UserInfoViewModel mUserInfoViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate that binds the contact list view model and connects to the get endpoint on the server.
     *
     * @param theSavedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle theSavedInstanceState) {
        super.onCreate(theSavedInstanceState);
//        mUserInfoViewModel = new ViewModelProvider(getActivity())
//                .get(UserInfoViewModel.class);
//        mHomeViewModel = new ViewModelProvider(getActivity())
//                .get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mHomeViewModel.connectGet(mUserInfoViewModel.getEmail(), mUserInfoViewModel.getmJwt());

        //Local access to the ViewBinding object. No need to create as Instance Var as it is only
        //used here.
        FragmentHomeBinding binding = FragmentHomeBinding.bind(getView());
        //Note argument sent to the ViewModelProvider constructor. It is the Activity that
        //holds this fragment.
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        binding.textEmail.setText(model.getEmail()); //textEmail will change when fragment is created.


//        int msgCount = mHomeViewModel.getNumMessages();
//        int contactCount = mHomeViewModel.getNumContacts();
//        if (msgCount > 0 || contactCount > 0){
//            Log.v(TAG, "light up icon");
//            binding.imageNotification.setColorFilter(Color.RED);
//        }
//
//        binding.imageNotification.setOnClickListener(button -> {
//            // TODO show user notifications.
//            binding.imageNotification.clearColorFilter();
//            mHomeViewModel.resetNotifications();
//        });
    }
}