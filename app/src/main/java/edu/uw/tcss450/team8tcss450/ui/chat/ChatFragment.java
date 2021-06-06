package edu.uw.tcss450.team8tcss450.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.contacts.Contact;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactListTabRecyclerViewAdapter;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactProfileFragmentDirections;

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

        //Listener for the search contact button.
        binding.buttonNewChatFragmentChat.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    ChatFragmentDirections.actionNavigationChatToNavigationContacts());
                    //Dire.actionContactProfileFragmentToChatTestFragment(args.getContactEmail()))

        });

        //listener for chatID list
        mChatListViewModel.addChatIDListObserver(getViewLifecycleOwner(), chatIDList -> {
            int chatIdNums = mChatListViewModel.getTotalNumberOfChatIDs();
            if (chatIDList.size() == mChatListViewModel.getTotalNumberOfChatIDs() && chatIdNums != 0) {
                for (int i = 0; i < chatIDList.size(); i++) { //DO I NEED THE FOR LOOP?
                    mChatListViewModel.connectMessages(chatIDList.get(i), mUserInfoViewModel.getmJwt());
                    //mChatListViewModel.connectGetUsernames(chatIDList.get(i), mUserInfoViewModel.getmJwt());
                }
            }
        });

        //listener for message map.
        mChatListViewModel.addMessageMapObserver(getViewLifecycleOwner(), messageMap -> {
            int listSize = messageMap.size();
            int totalChatRooms = mChatListViewModel.getTotalNumberOfChatIDs();
            ArrayList<Integer> chatIDList = (ArrayList<Integer>) mChatListViewModel.getChatIDs();
            if (messageMap.size() == totalChatRooms && totalChatRooms != 0) {
                for (int i = 0; i < listSize; i++) { //DO I NEED THE FOR LOOP?
                    //mChatListViewModel.connectMessages(chatIDList.get(i), mUserInfoViewModel.getmJwt());
                    mChatListViewModel.connectGetUsernames(chatIDList.get(i), mUserInfoViewModel.getmJwt());
                }
            }
        });

        //listener for chat list.
        mChatListViewModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            int chatIdNums = mChatListViewModel.getTotalNumberOfChatIDs();
            if (chatList.size() == mChatListViewModel.getTotalNumberOfChatIDs() && chatIdNums != 0) { //if new data is not showing up, clear map here
                binding.listRoot.setAdapter(
                        new ChatRecyclerViewAdapter(chatList)
                );
                //resets lists and numbers for each time chatId's is returned to. Needed to refresh data.
                mChatListViewModel.clearChatList();
                mChatListViewModel.clearNicknameList();
                mChatListViewModel.clearMessageMap();
                mChatListViewModel.setTotalNumberOfChatIDs(0);

            }
        });


    }

}


