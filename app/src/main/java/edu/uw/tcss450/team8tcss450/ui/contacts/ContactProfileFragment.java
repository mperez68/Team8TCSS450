package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactProfileBinding;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactsBinding;
import edu.uw.tcss450.team8tcss450.databinding.FragmentRegisterBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.auth.signin.SignInFragmentArgs;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactProfileFragment extends Fragment {

    private FragmentContactProfileBinding myBinding;

    public ContactProfileFragment() {

    }

    /**
     * onCreate that binds the contact list view model and connects to the get endpoint on the server.
     *
     * @param theSavedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle theSavedInstanceState) {
        super.onCreate(theSavedInstanceState);
    }

    /**
     * onCreate view that inflates the view with fragment contact.
     *
     * @param theInflater
     * @param theContainer
     * @param theSavedInstanceState
     * @return the inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater theInflater, ViewGroup theContainer,
                             Bundle theSavedInstanceState) {
        // Inflate the layout for this fragment
        myBinding = FragmentContactProfileBinding.inflate(theInflater);
        return myBinding.getRoot();
    }

    /**
     * onViewCreated adds listeners to the front end elements and retrieves arguments
     * passed to the fragment.
     *
     * @param theView
     * @param theSavedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View theView, @Nullable Bundle theSavedInstanceState) {
        super.onViewCreated(theView, theSavedInstanceState);

        ContactProfileFragmentArgs args = ContactProfileFragmentArgs.fromBundle(getArguments());

        //Listener for the search contact button.
        myBinding.buttonContactMessage.setOnClickListener(button ->
//                Navigation.findNavController(getView()).navigate(
//                        ContactProfileFragmentDirections.actionContactProfileFragmentToChatTestFragment(args.getContactEmail())));

        Navigation.findNavController(getView()).navigate(
                ContactProfileFragmentDirections.actionContactProfileFragmentToChatMessageFragment("Default")));
    }
}
