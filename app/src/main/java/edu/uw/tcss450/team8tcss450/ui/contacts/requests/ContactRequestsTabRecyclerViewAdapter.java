package edu.uw.tcss450.team8tcss450.ui.contacts.requests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactRequestCardBinding;
import edu.uw.tcss450.team8tcss450.ui.contacts.ContactsFragmentDirections;

public class ContactRequestsTabRecyclerViewAdapter extends RecyclerView.Adapter<ContactRequestsTabRecyclerViewAdapter.ContactRequestViewHolder> {

    //Store all of the blogs to present
    private final List<ContactRequest> mContactRequests;

    /**
     * Instantiate myContacts
     *
     * @params theItems list of contacts to be instantiated.
     */
    public ContactRequestsTabRecyclerViewAdapter(List<ContactRequest> theItems) {
        this.mContactRequests = theItems;
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
    public ContactRequestViewHolder onCreateViewHolder(@NonNull ViewGroup theParent, int theViewType) {
        return new ContactRequestsTabRecyclerViewAdapter.ContactRequestViewHolder(LayoutInflater
                .from(theParent.getContext())
                .inflate(R.layout.fragment_contact_request_card, theParent, false));
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
    public void onBindViewHolder(@NonNull ContactRequestsTabRecyclerViewAdapter.ContactRequestViewHolder theHolder, int thePosition) {
        theHolder.setContactRequests(mContactRequests.get(thePosition));
    }

    /**
     * getter for item count.
     * @return size of myContacts
     */
    @Override
    public int getItemCount() {
        return mContactRequests.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ContactRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public FragmentContactRequestCardBinding mBinding;
        private ContactRequest mContactRequest;

        /**
         * Constructor for the view and the binding.
         *
         * @params theView
         */
        public ContactRequestViewHolder(View theView) {
            super(theView);
            mView = theView;
            mBinding = FragmentContactRequestCardBinding.bind(theView);
            theView.setOnClickListener(this);
        }

        @Override
        public void onClick(View theView ) {
            Navigation.findNavController(theView).navigate(
                    ContactsFragmentDirections.actionNavigationContactsToContactRequestOptionsFragment(mContactRequest.getEmail())
            );
        }

        /**
         * Setter method for the contact.
         *
         * @params theContact
         */
        void setContactRequests(ContactRequest theRequest) {
            mContactRequest = theRequest;
            mBinding.contactEmail.setText(theRequest.getEmail());
        }
    }
}
