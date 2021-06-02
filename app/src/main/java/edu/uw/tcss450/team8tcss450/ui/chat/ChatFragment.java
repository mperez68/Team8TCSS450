package edu.uw.tcss450.team8tcss450.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;

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

        mChatListViewModel.connectNickName(mUserInfoViewModel.getEmail(), mUserInfoViewModel.getmJwt());
        mChatListViewModel.connectGetChatIDs(mUserInfoViewModel.getEmail(), mUserInfoViewModel.getmJwt());
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

        //add lister for the chatID list
        mChatListViewModel.addChatIDListObserver(getViewLifecycleOwner(), chatIDList -> {
            if (!chatIDList.isEmpty()) {
                for (int i = 0; i < chatIDList.size(); i++) {
                    mChatListViewModel.connectGetUsernames(chatIDList.get(i), mUserInfoViewModel.getmJwt());
                }
            }
        });

        mChatListViewModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {

            if (!chatList.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ChatRecyclerViewAdapter(chatList)
                );
            }
        });
    }

}


