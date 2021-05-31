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
 */
public class ChatRecyclerViewAdapter extends
        RecyclerView.Adapter<ChatRecyclerViewAdapter.ConversationViewHolder> {
    /**
     * The maximum number of character displayed when the conversation is expanded for preview.
     */
    private final int MAX_TEASER = 50;

    //Store all of the conversations to present
    private final List<ChatConversation> myConversations;

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
    public class ConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View myView;
        public FragmentChatCardBinding myBinding;
        private ChatConversation myConversation;

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

        /**
         * Onclick listener for buttons to send to chatTestFragment
         *
         * @params theView
         */
        @Override
        public void onClick(View theView) {
            String a = myConversation.getmEmail();
            Navigation.findNavController(theView).navigate(
                    ChatFragmentDirections.actionNavigationChatToChatTestFragment(myConversation.getmEmail())); //send contact name and chat ID as parameters
        }

        /**
         * sets the chat conversation card gotten from the endpoint in chatListViewModel.
         *
         * @params theChatConversation
         */
        private void setConversation(final ChatConversation theChatConversation) {
            myConversation = theChatConversation;


            //Use methods in the HTML class to format the HTML found in the text
            final String chatParticipants = theChatConversation.getmContact();
//            =Html.fromHtml(
//                    theChatConversation.getmContact(),
//                    Html.FROM_HTML_MODE_COMPACT)
//                    .toString().substring(0,Math.min(MAX_TEASER, theChatConversation.getmMessage().get(0).toString().length()-2));
            myBinding.textSender.setText(chatParticipants);

            myBinding.textTimestamp.setText("7/5");

            //Use methods in the HTML class to format the HTML found in the text

            //USED FOR LONG BACON MESSAAGE
//            final String preview = Html.fromHtml(
//                    theChatConversation.getmMessage().get(0).toString(),
//                    Html.FROM_HTML_MODE_COMPACT)
//                    .toString().substring(0,Math.min(MAX_TEASER, theChatConversation.getmMessage().get(0).toString().length()-2))
//                    + "...";
            final String preview = theChatConversation.getmMessage().get(0).toString();
            myBinding.textMessage.setText(preview);
        }
    }
}
