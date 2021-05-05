package edu.uw.tcss450.team8tcss450.ui.chat;

import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatCardBinding;

public class ChatRecyclerViewAdapter extends
        RecyclerView.Adapter<ChatRecyclerViewAdapter.ConversationViewHolder> {
    // TODO length checking
    private final int MAX_TEASER = 50;
    //Store the expanded state for each List item, true -> expanded, false -> not
    private final Map<ChatConversation, Boolean> mExpandedFlags;

    //Store all of the blogs to present
    private final List<ChatConversation> mConversations;
    public ChatRecyclerViewAdapter(List<ChatConversation> items) {
        this.mConversations = items;
        mExpandedFlags = mConversations.stream()
                .collect(Collectors.toMap(Function.identity(), blog -> false));
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.setConversation(mConversations.get(position));
    }

    @Override
    public int getItemCount() {
        return mConversations.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ConversationViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding binding;
        private ChatConversation mConversation;
        public ConversationViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
            binding.buttonMore.setOnClickListener(this::handleMoreOrLess);
        }
        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state. When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        private void handleMoreOrLess(final View button) {
            mExpandedFlags.put(mConversation, !mExpandedFlags.get(mConversation));
            displayPreview();
        }
        /**
         * Helper used to determine if the preview should be displayed or not.
         */
        private void displayPreview() {
            if (mExpandedFlags.get(mConversation)) {
                binding.textMessage.setVisibility(View.VISIBLE);
                binding.buttonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_less_grey_24dp));
            } else {
                binding.textMessage.setVisibility(View.GONE);
                binding.buttonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_more_grey_24dp));
            }
        }
        void setConversation(final edu.uw.tcss450.team8tcss450.ui.chat.ChatConversation chatConversations) {
            mConversation = chatConversations;
            binding.buttonFullPost.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                    ChatFragmentDirections
                        .actionNavigationChatToChatMesssageFragment(chatConversations));
            });
            binding.textSender.setText(chatConversations.getmContact());
            binding.textTimestamp.setText(Calendar.getInstance().getTime().toString());
//Use methods in the HTML class to format the HTML found in the text
            final String preview = Html.fromHtml(
                    chatConversations.getmMessage().get(0).toString(),
                    Html.FROM_HTML_MODE_COMPACT)
                    .toString().substring(0,Math.min(MAX_TEASER,chatConversations.getmMessage().get(0).toString().length()-2))
                    + "...";
            binding.textMessage.setText(preview);
            displayPreview();
        }
    }
}
