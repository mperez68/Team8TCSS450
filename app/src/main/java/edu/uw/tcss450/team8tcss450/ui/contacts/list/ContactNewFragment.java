package edu.uw.tcss450.team8tcss450.ui.contacts.list;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.team8tcss450.databinding.FragmentContactNewBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.requests.ContactRequestsTabViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactNewFragment extends Fragment {

    private FragmentContactNewBinding mBinding;
    private ContactSearchViewModel mContactSearchViewModel;
    private UserInfoViewModel mUserInfoViewModel;

    public ContactNewFragment() { }

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
        mContactSearchViewModel = new ViewModelProvider(getActivity())
                .get(ContactSearchViewModel.class);
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
        mBinding = FragmentContactNewBinding.inflate(theInflater);
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

        FragmentContactNewBinding binding = FragmentContactNewBinding.bind(getView());
        ContactRequestsTabViewModel contactRequestsTabViewModel = new ViewModelProvider(getActivity())
                .get(ContactRequestsTabViewModel.class);

        binding.buttonSearch.setOnClickListener(button -> {
            String email = binding.editTextEmail.getText().toString();

            if (email.equals(mUserInfoViewModel.getEmail())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Error");
                builder.setMessage("You are searching yourself!");
                builder.show();
            } else {
                mContactSearchViewModel.connectGet(email, mUserInfoViewModel.getmJwt());

                mContactSearchViewModel.searchContactObserver(getViewLifecycleOwner(), search ->
                    binding.editTextEmail.getText()
                );

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                if (mContactSearchViewModel.getSearch().getValue()) {
                    builder.setTitle("User found");
                    builder.setMessage("Send a contact request to this user?");

                    builder.setPositiveButton("Send request", (dialog, id) -> {
                        contactRequestsTabViewModel.connectPost(email, mUserInfoViewModel.getmJwt());
                    });

                    builder.setNegativeButton("Cancel", (dialog, id) -> {
                        return;
                    });
                } else {
                    builder.setTitle("Error");
                    builder.setMessage("User not found");
                }

                builder.show();
            }
        });
    }
}