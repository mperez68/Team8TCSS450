package edu.uw.tcss450.team8tcss450.ui.contacts.list;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.List;
import java.util.stream.IntStream;

import edu.uw.tcss450.team8tcss450.databinding.FragmentContactProfileBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.Contact;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactListTabViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactProfileFragment extends Fragment {

    private FragmentContactProfileBinding mBinding;
    private UserInfoViewModel mUserInfoViewModel;

    public ContactProfileFragment() {

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
        mBinding = FragmentContactProfileBinding.inflate(theInflater);
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

        ContactProfileFragmentArgs args = ContactProfileFragmentArgs.fromBundle(getArguments());

        ContactListTabViewModel contactListTabViewModel = new ViewModelProvider(getActivity())
                .get(ContactListTabViewModel.class);

        mBinding.buttonContactMessage.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ContactProfileFragmentDirections.actionContactProfileFragmentToChatTestFragment(args.getContactEmail()))
        );

        mBinding.buttonContactDelete.setOnClickListener(button -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete contact?");
            builder.setPositiveButton("Delete", (dialog, id) -> {
                        contactListTabViewModel.connectDelete(args.getContactEmail(), mUserInfoViewModel.getmJwt());
                        Navigation.findNavController(getView()).navigate(
                                ContactProfileFragmentDirections.actionContactProfileFragmentToNavigationContacts());
                        Toast.makeText(getActivity(), "Contact has been removed", Toast.LENGTH_SHORT).show();
                    });
            builder.setNegativeButton("Cancel", (dialog, id) ->
                    Toast.makeText(getActivity(), "Request has been cancelled", Toast.LENGTH_SHORT).show());
            builder.show();
        });

        List<Contact> contactList = contactListTabViewModel.getContactList().getValue();
        String contactEmail = args.getContactEmail();
        int index = IntStream.range(0, contactList.size())
                .filter(i -> contactList.get(i).getEmail().equals(contactEmail))
                .findFirst()
                .orElse(-1);

        mBinding.contactFirstname.setText(contactList.get(index).getFirstName());
        mBinding.contactLastname.setText(contactList.get(index).getLastName());
        mBinding.contactNickname.setText(contactList.get(index).getNickname());
    }
}