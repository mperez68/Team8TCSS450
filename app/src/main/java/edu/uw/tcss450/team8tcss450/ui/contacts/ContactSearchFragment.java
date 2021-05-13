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
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactSearchFragment extends Fragment {

    public FragmentContactSearchBinding myBinding;
    private ContactListViewModel myModel;

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
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        myModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        myModel.connectGet(model.getmJwt());
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
        myBinding = FragmentContactSearchBinding.inflate(theInflater);
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


        FragmentContactSearchBinding binding = FragmentContactSearchBinding.bind(getView());
        //Listener for the contacts recycler view adapter.
        //List<Contact> tempContactList = myModel.getContactList().getValue();

        //Listener for the search contact button.
        binding.buttonSearchContactSearch.setOnClickListener(button -> {
            String name = binding.contactSearchName.getText().toString();
            String userName = binding.contactSearchUsername.getText().toString();
            String email = binding.contactSearchEmail.getText().toString();

            List<Contact> tempContactList = myModel.getContactList().getValue();
            List<Contact> filteredList = new ArrayList<Contact>();

            for (int i = 0; i < tempContactList.size(); i++) {
                Contact temp = tempContactList.get(i);
                if (temp.getName().equals(name)) {
                    filteredList.add(temp);
                }

                if (temp.getUserName().equals(userName)) {
                    filteredList.add(temp);
                }

                if (temp.getEmail().equals(email)) {
                    filteredList.add(temp);
                }
            }

            List<Contact> resultList = removeDuplicates(filteredList);

            myModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
                if (!resultList.isEmpty()) {
                    binding.contactSearchListRoot.setAdapter(
                            new ContactsRecyclerViewAdapter(resultList)

                    );

                }
            });
        });

//        myModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
//            if (!contactList.isEmpty()) {
//                binding.contactSearchListRoot.setAdapter(
//                        myModel.getViewAdapter()
//
//                );
//
//            }
//        });
    }

    /**
     * helper method to remove duplicates from a list for the contact search feature.
     *
     * @param theFilteredList - search results.
     */
    public static List<Contact> removeDuplicates(List<Contact> theFilteredList) {


        // creating another array for only storing
        // the unique elements
        List<Contact> newList = new ArrayList<Contact>();


        // Traverse through the first list
        for (Contact element : theFilteredList) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}