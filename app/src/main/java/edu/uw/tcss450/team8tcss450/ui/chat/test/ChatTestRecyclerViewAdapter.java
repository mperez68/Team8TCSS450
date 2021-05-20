package edu.uw.tcss450.team8tcss450.ui.chat.test;

import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CornerFamily;

import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatMessageTestBinding;


/**
 * A recycler that holds a list of chat cards.
 */
public class ChatTestRecyclerViewAdapter extends RecyclerView.Adapter<ChatTestRecyclerViewAdapter.MessageViewHolder> {

    private final List<ChatTestMessage> mMessages;
    private final String mEmail;

    /**
     * ChatTestRecyclerViewAdapter constructor
     *
     * @param theMessages
     * @param theEmail
     */
    public ChatTestRecyclerViewAdapter(List<ChatTestMessage> theMessages, String theEmail) {
        this.mMessages = theMessages;
        mEmail = theEmail;
    }

    /**
     * Creates the holder for each chat message
     *
     * @param theParent
     * @param theViewType
     */
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup theParent, int theViewType) {
        return new MessageViewHolder(LayoutInflater
                .from(theParent.getContext())
                .inflate(R.layout.fragment_chat_message_test, theParent, false));
    }

    /**
     * The bind view holder
     *
     * @param theHolder
     * @param thePosition
     */
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder theHolder, int thePosition) {
        theHolder.setMessage(mMessages.get(thePosition));
    }

    /**
     * Getter for the number of messages in mMessages list.
     *
     * @return mMessages size
     */
    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    /**
     * Message view holder
     *
     */
    class MessageViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private FragmentChatMessageTestBinding binding;
//FragmentChatTestMessageBinding

        /**
         * Message view holder constructor
         *
         * @param theView
         *
         */
        public MessageViewHolder(@NonNull View theView) {
            super(theView);
            mView = theView;
            binding = FragmentChatMessageTestBinding.bind(theView);
        }

        /**
         * sets the chat message.
         *
         * @param theMessage
         *
         */
        void setMessage(final ChatTestMessage theMessage) {
            final Resources res = mView.getContext().getResources();
            final MaterialCardView card = binding.cardRoot;

            int standard = (int) res.getDimension(R.dimen.chat_margin);
            int extended = (int) res.getDimension(R.dimen.chat_margin_sided);

            if (mEmail.equals(theMessage.getSender())) {
                //This message is from the user. Format it as such
                binding.textMessage.setText(theMessage.getMessage());
                ViewGroup.MarginLayoutParams layoutParams =
                        (ViewGroup.MarginLayoutParams) card.getLayoutParams();
                //Set the left margin
                layoutParams.setMargins(extended, standard, standard, standard);
                // Set this View to the right (end) side
                ((FrameLayout.LayoutParams) card.getLayoutParams()).gravity =
                        Gravity.END;

                card.setCardBackgroundColor(
                        ColorUtils.setAlphaComponent(
                                res.getColor(R.color.primaryLightColor, null),
                                16));
                binding.textMessage.setTextColor(
                        res.getColor(R.color.secondaryTextColorFade, null));

                card.setStrokeWidth(standard / 5);
                card.setStrokeColor(ColorUtils.setAlphaComponent(
                        res.getColor(R.color.primaryLightColor, null),
                        200));

                //Round the corners on the left side
                card.setShapeAppearanceModel(
                        card.getShapeAppearanceModel()
                                .toBuilder()
                                .setTopLeftCorner(CornerFamily.ROUNDED,standard * 2)
                                .setBottomLeftCorner(CornerFamily.ROUNDED,standard * 2)
                                .setBottomRightCornerSize(0)
                                .setTopRightCornerSize(0)
                                .build());

                card.requestLayout();
            } else {
                //This message is from another user. Format it as such
                binding.textMessage.setText(theMessage.getSender() +
                        ": " + theMessage.getMessage());
                ViewGroup.MarginLayoutParams layoutParams =
                        (ViewGroup.MarginLayoutParams) card.getLayoutParams();

                //Set the right margin
                layoutParams.setMargins(standard, standard, extended, standard);
                // Set this View to the left (start) side
                ((FrameLayout.LayoutParams) card.getLayoutParams()).gravity =
                        Gravity.START;

                card.setCardBackgroundColor(
                        ColorUtils.setAlphaComponent(
                                res.getColor(R.color.secondaryLightColor, null),
                                16));

                card.setStrokeWidth(standard / 5);
                card.setStrokeColor(ColorUtils.setAlphaComponent(
                        res.getColor(R.color.secondaryLightColor, null),
                        200));

                binding.textMessage.setTextColor(
                        res.getColor(R.color.secondaryTextColorFade, null));

                //Round the corners on the right side
                card.setShapeAppearanceModel(
                        card.getShapeAppearanceModel()
                                .toBuilder()
                                .setTopRightCorner(CornerFamily.ROUNDED,standard * 2)
                                .setBottomRightCorner(CornerFamily.ROUNDED,standard * 2)
                                .setBottomLeftCornerSize(0)
                                .setTopLeftCornerSize(0)
                                .build());
                card.requestLayout();
            }
        }
    }
}
