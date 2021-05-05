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
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactsBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;

import edu.uw.tcss450.team8tcss450.ui.auth.signin.SignInFragmentDirections;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatListViewModel;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatRecyclerViewAdapter;


/**
 * TODO Filler Class, alter as needed.
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private ContactListViewModel mModel;

    //public FragmentChatBinding binding;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mModel.connectGet(model.getJWT().toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactsBinding binding = FragmentContactsBinding.bind(getView());

        //Listener for the contacts recycler view adapter.


        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.contactsListRoot.setAdapter(
                     mModel.getViewAdapter()

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