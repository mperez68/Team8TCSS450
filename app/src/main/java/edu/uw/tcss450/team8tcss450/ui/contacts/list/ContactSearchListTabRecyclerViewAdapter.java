package edu.uw.tcss450.team8tcss450.ui.contacts.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactListCardBinding;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactSearchListCardBinding;
import edu.uw.tcss450.team8tcss450.ui.contacts.Contact;
import edu.uw.tcss450.team8tcss450.ui.contacts.ContactsFragmentDirections;

/**
 * Adapter for the contacts recycler view.
 */
public class ContactSearchListTabRecyclerViewAdapter extends RecyclerView.Adapter<ContactSearchListTabRecyclerViewAdapter.ContactViewHolder> {

    //Store all of the blogs to present
    private final List<Contact> mContacts;

    /**
     * Instantiate myContacts
     *
     * @params theItems list of contacts to be instantiated.
     */
    public ContactSearchListTabRecyclerViewAdapter(List<Contact> theItems) {
        this.mContacts = theItems;
    }

    /**
     * inflate the viewholder with the card.
     *
     * @params theParent
     * @params theViewType
     *
     * @return ContactViewHolder
     */
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup theParent, int theViewType) {
        return new ContactViewHolder(LayoutInflater
                .from(theParent.getContext())
                .inflate(R.layout.fragment_contact_search_list_card, theParent, false));
    }

    /**
     * onBindViewHolder
     *
     * @params theParent
     * @params theViewType
     *
     * @return ContactViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull ContactSearchListTabRecyclerViewAdapter.ContactViewHolder theHolder, int thePosition) {
        theHolder.setContacts(mContacts.get(thePosition));
    }

    /**
     * getter for item count.
     * @return size of myContacts
     */
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public FragmentContactSearchListCardBinding mBinding;
        private Contact mContact;

        /**
         * Constructor for the view and the binding.
         *
         * @params theView
         */
        public ContactViewHolder(View theView) {
            super(theView);
            mView = theView;
            mBinding = FragmentContactSearchListCardBinding.bind(theView);
            theView.setOnClickListener(this);
        }

        @Override
        public void onClick(View theView) {
            String a = mContact.getNickname();
            String b = mContact.getEmail();
            Navigation.findNavController(theView).navigate(
                    ContactSearchFragmentDirections.actionContactSearchFragmentToContactProfileFragment(mContact.getNickname(), mContact.getEmail())



//                    ContactsFragmentDirections.actionNavigationContactsToContactProfileFragment(mContact.getNickname(), mContact.getEmail())
            );
        }

        /**
         * Setter method for the contact.
         *
         * @params theContact
         */
        void setContacts(Contact theContact) {
            mContact = theContact;
            mBinding.contactNickname.setText(theContact.getNickname());
        }
    }
}
