package edu.uw.tcss450.team8tcss450.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChatBinding;

/**
 * TODO Filler Class, alter as needed.
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    public FragmentChatBinding binding;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonOpenChat.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ChatFragmentDirections
                                .actionNavigationChatToChatListFragment()
                ));
    }
}