package edu.uw.tcss450.team8tcss450.ui.contacts.requests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.List;
import java.util.stream.IntStream;

import edu.uw.tcss450.team8tcss450.databinding.FragmentContactRequestOptionsBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.Contact;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactListTabViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactProfileFragmentArgs;

public class ContactRequestOptionsFragment extends Fragment {

    private FragmentContactRequestOptionsBinding mBinding;
    private UserInfoViewModel mUserInfoViewModel;

    public ContactRequestOptionsFragment() {

    }

    /**
     * onCreate that binds the contact list view model and connects to the get endpoint on the server.
     *
     * @param theSavedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle theSavedInstanceState) {
        super.onCreate(theSavedInstanceState);
        mUserInfoViewModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
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
        mBinding = FragmentContactRequestOptionsBinding.inflate(theInflater);
        return mBinding.getRoot();
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

        ContactRequestOptionsFragmentArgs args = ContactRequestOptionsFragmentArgs.fromBundle(getArguments());

        ContactListTabViewModel contactListTabViewModel = new ViewModelProvider(getActivity())
                .get(ContactListTabViewModel.class);

        ContactRequestsTabViewModel contactRequestsTabViewModel = new ViewModelProvider(getActivity())
                .get(ContactRequestsTabViewModel.class);

        mBinding.buttonRequestAccept.setOnClickListener(button -> {
            contactListTabViewModel.connectPost(mUserInfoViewModel.getEmail(), args.getRequestEmail(), mUserInfoViewModel.getmJwt());
            Navigation.findNavController(getView()).navigate(
                    ContactRequestOptionsFragmentDirections.actionContactRequestOptionsFragmentToNavigationContacts());
            Toast.makeText(getActivity(), "Request has been accepted", Toast.LENGTH_SHORT).show();
        });

        mBinding.buttonRequestDecline.setOnClickListener(button -> {
            contactRequestsTabViewModel.connectDelete(args.getRequestEmail(), mUserInfoViewModel.getmJwt());
            Navigation.findNavController(getView()).navigate(
                    ContactRequestOptionsFragmentDirections.actionContactRequestOptionsFragmentToNavigationContacts());
            Toast.makeText(getActivity(), "Request has been removed", Toast.LENGTH_SHORT).show();
        });

        mBinding.contactEmail.setText(args.getRequestEmail());
    }
}
