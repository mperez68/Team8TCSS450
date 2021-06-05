package edu.uw.tcss450.team8tcss450.ui.chat.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatTestBinding;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.ui.chat.ChatRecyclerViewAdapter;
import edu.uw.tcss450.team8tcss450.ui.contacts.Contact;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactProfileFragmentDirections;
import edu.uw.tcss450.team8tcss450.ui.contacts.list.ContactSearchListTabRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatTestFragment extends Fragment {



    //The chat ID for "global" chat
    private static final int HARD_CODED_CHAT_ID = 1;

    private ChatTestViewModel mChatModel;
    private UserInfoViewModel mUserModel;
    private ChatTestSendViewModel mSendModel;

    private String myUserEmail;
    private String myContactEmail;
    private int myChatID;

    public ChatTestFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate for the Chat Fragment.
     * @param theSavedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle theSavedInstanceState) {
        super.onCreate(theSavedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatTestViewModel.class);
        ChatTestFragmentArgs args = ChatTestFragmentArgs.fromBundle(getArguments());

        //HARD CODED -------------------

//        myChatID = args.getChatID();
//        mChatModel.getFirstMessages(myChatID, mUserModel.getmJwt()); //edited here from mUserModel.getmJWT from Lab 5

        //NEW IMPLEMENTATION -------------------


        myUserEmail = mUserModel.getEmail(); //my email
        myContactEmail = args.getContactEmail(); //the person who I want to chat with email


        mChatModel.connectChatID(myUserEmail + " and " + myContactEmail + "'s Private Chat", mUserModel.getmJwt(), myUserEmail, myContactEmail);

        mSendModel = provider.get(ChatTestSendViewModel.class);


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
        return theInflater.inflate(R.layout.fragment_chat_test, theContainer, false);
    }

    /**
     * onViewCreated for the Chat Fragment.
     * @param theView
     * @param theSavedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View theView, @Nullable Bundle theSavedInstanceState) {
        super.onViewCreated(theView, theSavedInstanceState);

        FragmentChatTestBinding binding = FragmentChatTestBinding.bind(getView());

        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerMessages;


//        //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
//        //holds.
//        rv.setAdapter(new ChatTestRecyclerViewAdapter(
//                mChatModel.getMessageListByChatId(myChatID),
//                mUserModel.getEmail()));
//
//
//        //When the user scrolls to the top of the RV, the swiper list will "refresh"
//        //The user is out of messages, go out to the service and get more
//        binding.swipeContainer.setOnRefreshListener(() -> {
//            mChatModel.getNextMessages(myChatID, mUserModel.getmJwt()); //edited here from mUserModel.getmJWT from Lab 5
//        });
//
//        mChatModel.addMessageObserver(myChatID, getViewLifecycleOwner(),
//                list -> {
//                    /*
//                     * This solution needs work on the scroll position. As a group,
//                     * you will need to come up with some solution to manage the
//                     * recyclerview scroll position. You also should consider a
//                     * solution for when the keyboard is on the screen.
//                     */
//                    //inform the RV that the underlying list has (possibly) changed
//                    rv.getAdapter().notifyDataSetChanged();
//                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
//                    binding.swipeContainer.setRefreshing(false);
//                });
//
//
//        //Send button was clicked. Send the message via the SendViewModel
//        binding.buttonSend.setOnClickListener(button -> {
//            mSendModel.sendMessage(myChatID,
//                    mUserModel.getmJwt(), //edited here from mUserModel.getmJWT from Lab 5,
//                    binding.editMessage.getText().toString());
//        });



        //DIVIDE---------------------------------------------------------------------------

        mChatModel.addChatIDObserver(this, chatID -> {
            if (chatID != -1) {
                myChatID = mChatModel.getChatID();
                mChatModel.addUsersToChat(myChatID, myUserEmail, mUserModel.getmJwt());
                mChatModel.addUsersToChat(myChatID, myContactEmail, mUserModel.getmJwt());

                mChatModel.getFirstMessages(myChatID, mUserModel.getmJwt()); //edited here from mUserModel.getmJWT from Lab 5

                //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
                //holds.
                rv.setAdapter(new ChatTestRecyclerViewAdapter(
                        mChatModel.getMessageListByChatId(myChatID),
                        mUserModel.getEmail()));


                //When the user scrolls to the top of the RV, the swiper list will "refresh"
                //The user is out of messages, go out to the service and get more
                binding.swipeContainer.setOnRefreshListener(() -> {
                    mChatModel.getNextMessages(myChatID, mUserModel.getmJwt()); //edited here from mUserModel.getmJWT from Lab 5
                });

                mChatModel.addMessageObserver(myChatID, getViewLifecycleOwner(),
                        list -> {
                            /*
                             * This solution needs work on the scroll position. As a group,
                             * you will need to come up with some solution to manage the
                             * recyclerview scroll position. You also should consider a
                             * solution for when the keyboard is on the screen.
                             */
                            //inform the RV that the underlying list has (possibly) changed
                            rv.getAdapter().notifyDataSetChanged();
                            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                            binding.swipeContainer.setRefreshing(false);
                        });


                //Send button was clicked. Send the message via the SendViewModel
                binding.buttonSend.setOnClickListener(button -> {
                    mSendModel.sendMessage(myChatID,
                            mUserModel.getmJwt(), //edited here from mUserModel.getmJWT from Lab 5,
                            binding.editMessage.getText().toString());
                });

                //Listener for the search contact button.
                binding.chatRoomLeave.setOnClickListener(button -> {
                    mChatModel.deleteChatRoom(myChatID, mUserModel.getmJwt());
                });


                mChatModel.addDeleteSuccessObserver(getViewLifecycleOwner(), deleteBool -> {
                    if (deleteBool) {
                        Navigation.findNavController(getView()).navigate(
                                ChatTestFragmentDirections.actionChatTestFragmentToNavigationHome());
                        mChatModel.setDeleteBoolean(false);
                    }
                });
                //restart so listener does not use previous chatID value.
                mChatModel.setMyChatID(-1);
            }
        });

        //when we get the response back from the server, clear the edittext
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response ->
                binding.editMessage.setText(""));


    }
}