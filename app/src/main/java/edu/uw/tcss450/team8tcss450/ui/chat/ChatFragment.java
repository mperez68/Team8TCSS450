package edu.uw.tcss450.team8tcss450.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.ContactsFragmentDirections;

/**
 * Chat Fragment for a recycler list of chat messages. Users navigate here from the bottom bar
 * and navigate to individual conversations by selecting which to read and reply to.
 * TODO be able to start new messages from here.
 *
 * @author Marc Perez
 * @version 6 May 2021
 */
public class ChatFragment extends Fragment {
    private ChatListViewModel mModel;
    public FragmentChatBinding binding;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        mModel.connectGet(model.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatBinding binding = FragmentChatBinding.bind(getView());
        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (!chatList.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ChatRecyclerViewAdapter(chatList)
                );
            }
        });

        //Listener for the chat test
        binding.testButton.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ChatFragmentDirections.actionNavigationChatToChatTestFragment()
                ));
    }
}