package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatBinding;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactCardBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactCardFragment extends Fragment {

    public FragmentContactCardBinding myBinding;

    /**
     * Empty constructor for the contact card.
     */
    public ContactCardFragment() {
        // Required empty constructor
    }

    /**
     * onCreate view that inflates myBinding.
     *
     * @param theInflater
     * @param theContainer
     * @param theSavedInstanceState
     * @return the root of the inflated binding.
     */
    @Override
    public View onCreateView(LayoutInflater theInflater, ViewGroup theContainer,
                             Bundle theSavedInstanceState) {
        myBinding = FragmentContactCardBinding.inflate(theInflater);
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
    }

}