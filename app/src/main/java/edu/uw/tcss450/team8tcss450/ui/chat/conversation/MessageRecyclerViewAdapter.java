package edu.uw.tcss450.team8tcss450.ui.chat.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatConversationMsgBinding;

/**
 * Modified from Charles Bryan's lab assignment for the course TCSS 450.
 *
 * @author Charles Bryan
 * @version
 * @author Marc Perez
 * @version 6 May 2021
 */
public class MessageRecyclerViewAdapter extends
        RecyclerView.Adapter<MessageRecyclerViewAdapter.MessageViewHolder> {

    //Store all of the messages to present
    private final List<Message> mMessages;

    public MessageRecyclerViewAdapter(List<Message> items) {
        this.mMessages = items;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_conversation_msg, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.setMessages(mMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class MessageViewHolder extends RecyclerView.ViewHolder {
        /**
         * View object used in the current Fragment.
         */
        public final View mView;
        /**
         * Binding object that handles buttons/text/etc. on the message card.
         */
        public FragmentChatConversationMsgBinding binding;
        /**
         * message object for this individual message holder.
         * TODO analyize if still needed, it is assigned but not referenced.
         */
        private Message mMessage;

        /**
         * Public constructor.
         * @param view View object used in the current Fragment.
         */
        public MessageViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatConversationMsgBinding.bind(view);
        }

        /**
         * Assigns the message being held in this holder.
         * @param message message object for this individual message holder.
         */
        void setMessages(final Message message) {
            mMessage = message;
            binding.textSender.setText(message.getName());
            binding.textMessage.setText(message.getMesssage());
            binding.textTimestamp.setText(message.getmTimeStamp());
        }
    }
}
