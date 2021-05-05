package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactSearchBinding;

import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactSearchFragment extends Fragment {

    private ContactListViewModel mModel;
    public FragmentContactSearchBinding binding;

    public ContactSearchFragment() {
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
        //binding = FragmentContactSearchBinding.inflate(inflater);
        //return binding.getRoot();
        return inflater.inflate(R.layout.fragment_contact_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactSearchBinding binding = FragmentContactSearchBinding.bind(getView());
        //Listener for the contacts recycler view adapter.
        //List<Contact> tempContactList = mModel.getContactList().getValue();



        //Listener for the search contact button.
        binding.buttonSearchContactSearch.setOnClickListener(button -> {
            String name = binding.contactSearchName.getText().toString();
            String userName = binding.contactSearchUsername.getText().toString();
            String email = binding.contactSearchEmail.getText().toString();

            List<Contact> tempContactList = mModel.getContactList().getValue();
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


            mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
                if (!resultList.isEmpty()) {
                    binding.contactSearchListRoot.setAdapter(
                            new ContactsRecyclerViewAdapter(resultList)

                    );

                }
            });

        });

//        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
//            if (!contactList.isEmpty()) {
//                binding.contactSearchListRoot.setAdapter(
//                        mModel.getViewAdapter()
//
//                );
//
//            }
//        });

    }

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