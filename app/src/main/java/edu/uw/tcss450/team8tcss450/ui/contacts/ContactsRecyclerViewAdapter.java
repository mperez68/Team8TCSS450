package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactCardBinding;

/**
 * Adapter for the contacts recycler view.
 */
public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder> {

    //Store all of the blogs to present
    private final List<Contact> myContacts;

    /**
     * Instantiate myContacts
     *
     * @params theItems list of contacts to be instantiated.
     */
    public ContactsRecyclerViewAdapter(List<Contact> theItems) {
        this.myContacts = theItems;
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
                .inflate(R.layout.fragment_contact_card, theParent, false));
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
    public void onBindViewHolder(@NonNull ContactsRecyclerViewAdapter.ContactViewHolder theHolder, int thePosition) {
        theHolder.setContacts(myContacts.get(thePosition));
    }

    /**
     * getter for item count.
     * @return size of myContacts
     */
    @Override
    public int getItemCount() {
        return myContacts.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View myView;
        public FragmentContactCardBinding myBinding;
        private Contact myContact;

        /**
         * Constructor for the view and the binding.
         *
         * @params theView
         */
        public ContactViewHolder(View theView) {
            super(theView);
            myView = theView;
            myBinding = FragmentContactCardBinding.bind(theView);
            theView.setOnClickListener(this);
        }

        @Override
        public void onClick(View theView) {
            Navigation.findNavController(theView).navigate(
                    ContactsFragmentDirections.actionNavigationContactsToContactProfileFragment(myContact.getName()));
        }

        /**
         * Setter method for the contact.
         *
         * @params theContact
         */
        void setContacts(Contact theContact) {
            myContact = theContact;
            myBinding.contactNickname.setText(theContact.getUserName());
        }
    }
}