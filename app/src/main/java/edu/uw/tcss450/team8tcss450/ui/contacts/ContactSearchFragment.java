package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.contactSearchListRoot.setAdapter(
                        new ContactsRecyclerViewAdapter(contactList)
                );

            }
        });

    }

}