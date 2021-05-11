package edu.uw.tcss450.team8tcss450.ui.chat;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatCardBinding;

/**
<<<<<<< HEAD
 * Adapter for the contacts recycler view.
=======
 * Modified from Charles Bryan's lab assignment for the course TCSS 450.
 *
 * Adapter class for the Chat Fragment class. This populates and manages the conversation list
 * and sets listeners to navigate to individual conversation fragments.
 * TODO establish a way to update open messages from an active background service.
 *
 * @author Charles Bryan
 * @version
 * @author Marc Perez
 * @version 6 May 2021
>>>>>>> 821a2a8f069b50da4d4900f72e29f4df2bb67663
 */
public class ChatRecyclerViewAdapter extends
        RecyclerView.Adapter<ChatRecyclerViewAdapter.ConversationViewHolder> {
    /**
     * The maximum number of character displayed when the conversation is expanded for preview.
     */
    private final int MAX_TEASER = 50;
<<<<<<< HEAD

    //Store all of the conversations to present
    private final List<ChatConversation> myConversations;
=======
    /**
     * Store the expanded state for each List item, true -> expanded, false -> not
     */
    private final Map<ChatConversation, Boolean> mExpandedFlags;

    /**
     * Store all of the conversations to present
     */
    private final List<ChatConversation> mConversations;
>>>>>>> 821a2a8f069b50da4d4900f72e29f4df2bb67663

    /**
     * Instantiate myConversations
     *
     * @params theItems list of conversations to be instantiated.
     */
    public ChatRecyclerViewAdapter(List<ChatConversation> theItems) {
        this.myConversations = theItems;
    }

    /**
     * inflate the viewholder with the card.
     *
     * @params theParent
     * @params theViewType
     *
     * @return ConversationViewHolder
     */
    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup theParent, int theViewType) {
        return new ConversationViewHolder(LayoutInflater
                .from(theParent.getContext())
                .inflate(R.layout.fragment_chat_card, theParent, false));
    }

    /**
     * onBindViewHolder
     *
     * @params theParent
     * @params theViewType
     *
     * @return ConversationViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder theHolder, int thePosition) {
        theHolder.setConversation(myConversations.get(thePosition));
    }

    /**
     * getter for item count.
     * @return size of myConversations
     */
    @Override
    public int getItemCount() {
        return myConversations.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
<<<<<<< HEAD
    public class ConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View myView;
        public FragmentChatCardBinding myBinding;
        private ChatConversation myConversation;

=======
    public class ConversationViewHolder extends RecyclerView.ViewHolder {
        /**
         * View object used in the current Fragment.
         */
        public final View mView;
        /**
         * Binding object that handles buttons/text/etc. on the chat card.
         */
        public FragmentChatCardBinding binding;
        /**
         * Conversation object for this individual conversation holder.
         */
        private ChatConversation mConversation;

        public ConversationViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
            //binding.buttonMore.setOnClickListener(this::handleMoreOrLess);
        }
>>>>>>> 821a2a8f069b50da4d4900f72e29f4df2bb67663
        /**
         * Constructor for the view and the binding.
         *
         * @params theView
         */
        public ConversationViewHolder(View theView) {
            super(theView);
            myView = theView;
            myBinding = FragmentChatCardBinding.bind(theView);
            theView.setOnClickListener(this);
        }

        @Override
        public void onClick(View theView) {
            Navigation.findNavController(theView).navigate(
                    ChatFragmentDirections.actionNavigationChatToChatMessageFragment(myConversation.getmContact()));
        }


        private void setConversation(final ChatConversation theChatConversation) {
            myConversation = theChatConversation;
            myBinding.textSender.setText("Contact Nickname");
            myBinding.textTimestamp.setText("7/5");

            //Use methods in the HTML class to format the HTML found in the text
            final String preview = Html.fromHtml(
                    theChatConversation.getmMessage().get(0).toString(),
                    Html.FROM_HTML_MODE_COMPACT)
                    .toString().substring(0,Math.min(MAX_TEASER, theChatConversation.getmMessage().get(0).toString().length()-2))
                    + "...";

            myBinding.textMessage.setText(preview);
        }
    }
}
