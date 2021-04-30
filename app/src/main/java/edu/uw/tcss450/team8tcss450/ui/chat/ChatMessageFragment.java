package edu.uw.tcss450.team8tcss450.ui.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatConversationBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatMessageFragment extends Fragment {
    public ChatMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChatMessageFragmentArgs args = ChatMessageFragmentArgs.fromBundle(getArguments());
        FragmentChatConversationBinding binding = FragmentChatConversationBinding.bind(getView());
        binding.textSender.setText(args.getConversation().getmContact());
        final String message = Html.fromHtml(
                args.getConversation().getmMessage(),
                Html.FROM_HTML_MODE_COMPACT)
                .toString();
        binding.textMessage.setText(message);
        final String timestamp = Calendar.getInstance().getTime().toString();
        binding.textTimestamp.setText(timestamp);
//Note we are using an Intent here to start the default system web browser
//        binding.buttonUrl.setOnClickListener(button ->
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(args.getConversation().getUrl()))));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_conversation, container, false);
    }
}