package edu.uw.tcss450.team8tcss450.ui.contacts.list;

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
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactListBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.ContactsFragmentDirections;

/**
 *
 * A simple {@link Fragment} subclass.
 */
public class ContactListTabFragment extends Fragment {

    private ContactListTabViewModel mContactListTabViewModel;
    private UserInfoViewModel mUserInfoViewModel;

    /**
     * empty constructor.
     */
    public ContactListTabFragment() {
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
        mUserInfoViewModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        mContactListTabViewModel = new ViewModelProvider(getActivity())
                .get(ContactListTabViewModel.class);
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
        return theInflater.inflate(R.layout.fragment_contact_list, theContainer, false);
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
        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());

        mContactListTabViewModel.getContactList().getValue().clear();
        mContactListTabViewModel.connectGet(mUserInfoViewModel.getEmail(), mUserInfoViewModel.getmJwt());

        mContactListTabViewModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            binding.contactListRoot.setAdapter(
                    mContactListTabViewModel.getViewAdapter()
            );
        });

        //Listener for the search contact button.
        binding.buttonSearchContacts.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ContactsFragmentDirections.actionNavigationContactsToContactSearchFragment()
                ));

        binding.buttonCreateContact.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ContactsFragmentDirections.actionNavigationContactsToContactNewFragment()
                ));
    }
}
