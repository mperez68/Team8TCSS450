package edu.uw.tcss450.team8tcss450.ui.contacts.requests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactRequestsBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;

/**
 *
 * A simple {@link Fragment} subclass.
 */
public class ContactRequestsTabFragment extends Fragment {

    private ContactRequestsTabViewModel mContactRequestsTabViewModel;
    private UserInfoViewModel mUserInfoViewModel;
    private ContactRequestsTabRecyclerViewAdapter mViewAdapter;

    public ContactRequestsTabFragment() {

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
        mContactRequestsTabViewModel = new ViewModelProvider(getActivity())
                .get(ContactRequestsTabViewModel.class);
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
        return theInflater.inflate(R.layout.fragment_contact_requests, theContainer, false);
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
        FragmentContactRequestsBinding binding = FragmentContactRequestsBinding.bind(getView());

        mContactRequestsTabViewModel.getRequestList().getValue().clear();
        mContactRequestsTabViewModel.connectGet(mUserInfoViewModel.getEmail(), mUserInfoViewModel.getmJwt());

        mContactRequestsTabViewModel.addContactRequestObserver(getViewLifecycleOwner(), requestList ->
            binding.contactRequestsRoot.setAdapter(
                    mContactRequestsTabViewModel.getViewAdapter())
        );

        mContactRequestsTabViewModel.getViewAdapter().notifyDataSetChanged();
    }
}
