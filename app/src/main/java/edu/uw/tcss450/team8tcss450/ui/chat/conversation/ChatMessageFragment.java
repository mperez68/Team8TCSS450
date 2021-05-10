package edu.uw.tcss450.team8tcss450.ui.chat.conversation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatConversationBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;

/**
 * Fragment that follows selecting an individual conversation to access. It creates a recycler view
 * list of all messages retrieved from server in chronological order. It allows the user to send
 * messages to the database and thus to the other user.
 *
 * @author Marc Perez
 * @version 6 May 2021
 */
public class ChatMessageFragment extends Fragment {
    /**
     * List View Model object that containes the messages in this conversation.
     */
    private MessageListViewModel mModel;

    /**
     * Public constructor.
     */
    public ChatMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(MessageListViewModel.class);
        mModel.connectGet(model.getJWT().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_conversation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatConversationBinding binding = FragmentChatConversationBinding.bind(getView());
        mModel.addChatListObserver(getViewLifecycleOwner(), messageList -> {
            if (!messageList.isEmpty()) {
                binding.listRoot.setAdapter(
                        new MessageRecyclerViewAdapter(messageList)
                );
                binding.listRoot.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        binding.textContact.setText("My Dearest Friend #?");
    }
}