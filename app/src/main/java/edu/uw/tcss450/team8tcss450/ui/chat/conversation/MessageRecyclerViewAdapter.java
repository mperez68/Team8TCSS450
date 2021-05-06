package edu.uw.tcss450.team8tcss450.ui.chat.conversation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatConversationMsgBinding;

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
        public final View mView;
        public FragmentChatConversationMsgBinding binding;
        private Message mMessage;

        public MessageViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatConversationMsgBinding.bind(view);
        }

        void setMessages(final Message message) {
            mMessage = message;
            binding.textSender.setText(message.getName());
            binding.textMessage.setText(message.getMesssage());
            binding.textTimestamp.setText(message.getTime());
        }
    }
}
