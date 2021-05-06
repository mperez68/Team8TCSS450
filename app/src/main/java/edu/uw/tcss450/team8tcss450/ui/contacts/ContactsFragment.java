package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatListBinding;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactsBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.auth.signin.SignInFragmentDirections;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatListViewModel;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatRecyclerViewAdapter;

/**
 *
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private ContactListViewModel myModel;

    //public FragmentChatBinding binding;

    /**
     * empty constructor.
     *
     */
    public ContactsFragment() {
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
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        myModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        myModel.connectGet(model.getJWT().toString());

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
        return theInflater.inflate(R.layout.fragment_contacts, theContainer, false);
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
        FragmentContactsBinding binding = FragmentContactsBinding.bind(getView());

        //Listener for the contacts recycler view adapter.


        myModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.contactsListRoot.setAdapter(
                     myModel.getViewAdapter()

                );

            }
        });

        //Listener for the search contact button.
        binding.buttonSearchContacts.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ContactsFragmentDirections.actionNavigationContactsToContactSearchFragment()
                ));

    }
}