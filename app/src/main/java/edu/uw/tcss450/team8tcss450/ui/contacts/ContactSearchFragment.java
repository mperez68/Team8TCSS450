package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team8tcss450.databinding.FragmentContactSearchBinding;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactListTabViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactListTabRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactSearchFragment extends Fragment {

    private FragmentContactSearchBinding mBinding;
    private ContactListTabViewModel mContactListTabViewModel;

    /**
     * empty public constructor.
     */
    public ContactSearchFragment() {
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

        mContactListTabViewModel = new ViewModelProvider(getActivity()).get(ContactListTabViewModel.class);
    }

    /**
     * onCreate view that inflates the view with fragment contact search..
     *
     * @param theInflater
     * @param theContainer
     * @param theSavedInstanceState
     * @return the inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater theInflater, ViewGroup theContainer,
                             Bundle theSavedInstanceState) {
        mBinding = FragmentContactSearchBinding.inflate(theInflater);
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


        FragmentContactSearchBinding binding = FragmentContactSearchBinding.bind(getView());

        //Listener for the search contact button.
        binding.buttonSearchContactSearch.setOnClickListener(button -> {
            String name = binding.contactSearchName.getText().toString();
            String nickname = binding.contactSearchNickname.getText().toString();
            String email = binding.contactSearchEmail.getText().toString();

            List<Contact> tempContactList = mContactListTabViewModel.getContactList().getValue();
            List<Contact> filteredList = new ArrayList<>();

            for (int i = 0; i < tempContactList.size(); i++) {
                Contact temp = tempContactList.get(i);
                if (temp.getName().equals(name)) {
                    filteredList.add(temp);
                }

                if (temp.getNickname().equals(nickname)) {
                    filteredList.add(temp);
                }

                if (temp.getEmail().equals(email)) {
                    filteredList.add(temp);
                }
            }

            mContactListTabViewModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
                if (!filteredList.isEmpty()) {
                    binding.contactSearchListRoot.setAdapter(
                            new ContactListTabRecyclerViewAdapter(filteredList)
                    );
                }
            });
        });

        mContactListTabViewModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.contactSearchListRoot.setAdapter(
                        mContactListTabViewModel.getViewAdapter()
                );
            }
        });
    }
}