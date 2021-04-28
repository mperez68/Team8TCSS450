package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactsBinding;

/**
 * TODO Filler Class, alter as needed.
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    public FragmentContactsBinding binding;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater);
        return binding.getRoot();
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.buttonSearchContacts.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ContactsFragmentDirections
                                .actionNavigationContactsToContactSearchFragment())
        );
    }
}