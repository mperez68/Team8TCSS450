package edu.uw.tcss450.team8tcss450.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.uw.tcss450.team8tcss450.databinding.FragmentFirstBinding;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements View.OnClickListener {
    private FragmentFirstBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Is called after view hierarchy is created.
     * @param view ..
     * @param savedInstanceState ..
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //add this Fragment Object as the OnClickListener to the Red button
        binding.buttonRed.setOnClickListener(this);
        //Use a Lamda expression to add the OnClickListener
        binding.buttonGreen.setOnClickListener(button ->
                processColor(Color.GREEN));
        //Use a method reference to add the OnClickListener
        binding.buttonBlue.setOnClickListener(this::handleBlue);
    }

    /**
     * Deconstructor function.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Implementation of View.OnClickListener for the red button click listener.
     * @param view ..
     */
    @Override
    public void onClick(View view) {
        if (view == binding.buttonRed){
            processColor(Color.RED);
        }
    }

    /**
     * Listener function for the blue button.
     * @param v ..
     */
    private void handleBlue(View v){
        if (v == binding.buttonBlue){
            processColor(Color.BLUE);
        }
    }

    /**
     * Helper function to process buttons being clicked.
     * @param color integer code for the color being processed.
     */
    public void processColor(int color) {
        Log.d("ACTIVITY", "Red: " + Color.red(color) +
                " Green: " + Color.green(color) +
                " Blue: " + Color.blue(color));
        //The following object represents the action from first to color.
        FirstFragmentDirections.ActionFirstFragmentToColorFragment directions = FirstFragmentDirections.actionFirstFragmentToColorFragment(color);

        //Use the navigate method to perform the navigation.
        Navigation.findNavController(getView()).navigate(directions);
    }
}