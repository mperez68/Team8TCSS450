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

import java.util.List;

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
    private ChatListViewModel mChatListViewModel;
    public FragmentChatBinding mBinding;
    public UserInfoViewModel mUserInfoViewModel;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate for the Chat Fragment.
     * @param theSavedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle theSavedInstanceState) {
        super.onCreate(theSavedInstanceState);
        mUserInfoViewModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        mChatListViewModel = new ViewModelProvider(getActivity())
                .get(ChatListViewModel.class);

        //TODO this is 4 asynchronous calls for hard coded chat rooms 1,2,3, and 4. This will need to be reworked.
        for (int i = 1; i <= 4; i++) {
            mChatListViewModel.connectGet(mUserInfoViewModel.getmJwt(), i);
        }
        //Original
       // mModel.connectGet(model.getmJwt());
    }

    /**
     * onCreate for the Chat Fragment.
     * @param theInflater
     * @param theContainer
     * @param theSavedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater theInflater, ViewGroup theContainer,
                             Bundle theSavedInstanceState) {
        // Inflate the layout for this fragment
        return theInflater.inflate(R.layout.fragment_chat, theContainer, false);
    }

    /**
     * onViewCreated for the Chat Fragment.
     * @param theView
     * @param theSavedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View theView, @Nullable Bundle theSavedInstanceState) {
        super.onViewCreated(theView, theSavedInstanceState);
        FragmentChatBinding binding = FragmentChatBinding.bind(getView());

        mChatListViewModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            // TODO this is hard coded for the 4 chat rooms. In the future this will require broader implementation. Original down below.
            if (!chatList.isEmpty()) {
                int size = chatList.size();
                for (int i = 0; i < size; i++) {
                    if (!(chatList.get(i).getmContact().contains(mUserInfoViewModel.getEmail()))) {
                        chatList.remove(i);
                    }
                }
                binding.listRoot.setAdapter(
                        new ChatRecyclerViewAdapter(chatList)
                );
            }
        });
        //Original
//        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
//            if (!chatList.isEmpty()) {
//                binding.listRoot.setAdapter(
//                        new ChatRecyclerViewAdapter(chatList)
//                );
//            }
//        });

        //Listener for the chat test
        binding.testButton.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ChatFragmentDirections.actionNavigationChatToChatTestFragment("test1@test.com", 1) //these parameters are trivial
                ));
    }

}


