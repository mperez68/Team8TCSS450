package edu.uw.tcss450.team8tcss450.ui.contacts;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatCardBinding;
import edu.uw.tcss450.team8tcss450.databinding.FragmentContactCardBinding;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatConversation;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatFragmentDirections;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatRecyclerViewAdapter;

/**
 * Adapter for the contacts recycler view.
 */
public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder> {
    private final int MAX_TEASER = 50; //needed?

    //Store all of the blogs to present
    private final List<Contact> myContacts;

    /**
     * Instantiate myContacts
     *
     * @params theItems list of contacts to be instantiated.
     */
    public ContactsRecyclerViewAdapter(List<Contact> theItems) {
        this.myContacts = theItems;
        //mExpandedFlags = mContacts.stream().collect(Collectors.toMap(Function.identity(), blog -> false));

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
    public class ContactViewHolder extends RecyclerView.ViewHolder {
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


            //Listener for the search contact button.
//            binding.contactButtonMessage.setOnClickListener(button ->
//                    Navigation.findNavController(view).navigate(
//                            ContactsFragmentDirections.actionNavigationContactsToContactSearchFragment()
//                    ));
            //binding.buttonMore.setOnClickListener(this::handleMoreOrLess); NEEDED? Ask team members
        }



        /**
         * Helper used to determine if the preview should be displayed or not.
         */
//        private void displayPreview() {
//            if (mExpandedFlags.get(mConversation)) {
//                binding.textMessage.setVisibility(View.VISIBLE);
//                binding.buttonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_less_grey_24dp));
//            } else {
//                binding.textMessage.setVisibility(View.GONE);
//                binding.buttonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_more_grey_24dp));
//            }
//        }


        /**
         * setter for the contact.
         *
         * @params theContact
         */
        void setContacts(Contact theContact) {
            myContact = theContact;
            myBinding.contactButtonMessage.setOnClickListener(view -> {
                //TODO NAVIGATE TO MESSAGE SCREEN
            });
            myBinding.contactFirstName.setText(theContact.getName());
            myBinding.contactUsername.setText(theContact.getUserName());
            myBinding.contactEmail.setText(theContact.getEmail());

           // binding.contactCardUserImage.setImageIcon() SET IMAGE ICON

//Use methods in the HTML class to format the HTML found in the text
           // final String preview = Html.fromHtml();
           // displayPreview();
        }

    }

}

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        // Your holder should contain a member variable
//        // for any view that will be set as you render a row
//
//        private final int MAX_TEASER = 50; //needed?
//        //Store the expanded state for each List item, true -> expanded, false -> not
//        private final Map<Contact, Boolean> mExpandedFlags;
//
//        //Store all of the blogs to present
//        private final List<Contact> mContacts;
////        public FragmentContactCardBinding binding;
////        public final View mView;
////        public TextView myNameTextView;
////        public TextView myUserNameTextView;
////        public TextView myEmailTextView;
////        public Button myMessageButton;
////        public Button messageButton;
//
//        // We also create a constructor that accepts the entire item row
//        // and does the view lookups to find each subview
//        public ViewHolder(View itemView) {
//            // Stores the itemView in a public final member variable that can be used
//            // to access the context from any ViewHolder instance.
//            super(itemView);
//            mView = itemView;
//            binding = FragmentContactCardBinding.bind(itemView);
//
//            myNameTextView = binding.contactFirstName;
//            myUserNameTextView = binding.contactUsername;
//            myEmailTextView = binding.contactEmail;
//            myMessageButton = binding.contactButtonMessage;
//
//
//
////            name = (TextView) itemView.findViewById(R.id.contact_name);
////            messageButton = (Button) itemView.findViewById(R.id.message_button);
//        }
//    }

    // Store a member variable for the contacts
//    private List<Contact> myContacts;
//
//    // Pass in the contact array into the constructor
//    public ContactsRecyclerViewAdapter(List<Contact> theContacts) {
//        myContacts = theContacts;
//    }
//
//    @NonNull
//    @Override
//    public ContactsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        // Inflate the custom layout
//        View contactView = inflater.inflate(R.layout.fragment_contact_card, parent, false);
//
//        // Return a new holder instance
//        ViewHolder viewHolder = new ViewHolder(contactView);
//        return viewHolder;
//
//        //Quicker way
////        return new ContactsAdapter.ViewHolder(LayoutInflater
////                .from(parent.getContext())
////                .inflate(R.layout.fragment_contact_card, parent, false));
//
//    }
//
//    // Involves populating data into the item through holder
//    @Override
//    public void onBindViewHolder(ContactsRecyclerViewAdapter.ViewHolder holder, int position) {
//        // Get the data model based on position
//        Contact contact = myContacts.get(position);
//
//        // Set item views based on your views and data model
//        TextView textView = holder.myNameTextView;
//        holder.binding.contactFirstName.setText(contact.getName());
//        holder.binding.contactUsername.setText(contact.getUserName());
//        holder.binding.contactEmail.setText(contact.getEmail());
//        //holder.binding.contactButtonMessage.setText(contact.isOnline() ? "Message" : "Offline");
//        //holder.binding.contactButtonMessage.setEnabled(contact.isOnline());
//    }
//
//    // Returns the total count of items in the list
//    @Override
//    public int getItemCount() {
//        return myContacts.size();
//    }
//
//}
//    //Store all of the blogs to present
//    private final List<Contacts> myContacts;
//
//    //Store the expanded state for each List item, true -> expanded, false -> not
//    private final Map<Contacts, Boolean> myExpandedFlags;
//
//
//    public ContactsAdapter(List<Contacts> theItems) {
//        this.myContacts = theItems;
//        myExpandedFlags = myContacts.stream()
//                .collect(Collectors.toMap(Function.identity(), blog -> false));
//
//    }
//
//    @NonNull
//    @Override
//    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ContactViewHolder(LayoutInflater
//                .from(parent.getContext())
//                .inflate(R.layout.fragment_contact_card, parent, false));
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
//        holder.setContact(myContacts.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return myContacts.size();
//    }
//
//    /**
//     * Objects from this class represent an Individual row View from the List
//     * of rows in the Blog Recycler View.
//     */
//    public class ContactsViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public FragmentContactCardBinding binding;
//        private Contacts myContacts;
//
//        public ContactsViewHolder(View view) {
//            super(view);
//            mView = view;
//            binding = FragmentContactCardBinding.bind(view);
//            binding.contactButtonMessage.setOnClickListener(this::handleMessageClick);
//
//        }
//
//        /**
//         * When the button is clicked in the more state, expand the card to display
//         * the blog preview and switch the icon to the less state.  When the button
//         * is clicked in the less state, shrink the card and switch the icon to the
//         * more state.
//         * @param button the button that was clicked
//         */
//        private void handleMessageClick(final View button) {
//            //Do something
//           // mExpandedFlags.put(mBlog, !mExpandedFlags.get(mBlog));
//            //displayPreview();
//        }
//
//        /**
//         * Helper used to determine if the preview should be displayed or not.
//         */
////        private void displayPreview() {
////            if (mExpandedFlags.get(mBlog)) {
////                //if (binding.textPreview.getVisibility() == View.GONE) {
////                binding.textPreview.setVisibility(View.VISIBLE);
////                binding.buittonMore.setImageIcon(
////                        Icon.createWithResource(
////                                mView.getContext(),
////                                R.drawable.ic_less_grey_24dp));
////            } else {
////                binding.textPreview.setVisibility(View.GONE);
////                binding.buittonMore.setImageIcon(
////                        Icon.createWithResource(
////                                mView.getContext(),
////                                R.drawable.ic_more_grey_24dp));
////            }
////        }
//
//        void setContact(final Contacts theContact) {
//            myContacts = theContact;
//            binding.contactButtonMessage.setOnClickListener(view -> {
////                Navigation.findNavController(mView).navigate(
////                        BlogListFragmentDirections
////                                .actionNavigationBlogsToBlogPostFragment(blog));
////
////                //TODO add navigation later step
//            });
//            binding.textTitle.setText(blog.getTitle());
//            binding.textPubdate.setText(blog.getPubDate());
//            //Use methods in the HTML class to format the HTML found in the text
//            final String preview =  Html.fromHtml(
//                    blog.getTeaser(),
//                    Html.FROM_HTML_MODE_COMPACT)
//                    .toString().substring(0,100) //just a preview of the teaser
//                    + "...";
//            binding.textPreview.setText(preview);
//            displayPreview();
//        }
//    }


