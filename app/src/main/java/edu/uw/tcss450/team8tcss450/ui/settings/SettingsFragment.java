package edu.uw.tcss450.team8tcss450.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import androidx.navigation.Navigation;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentSettingsBinding;
import edu.uw.tcss450.team8tcss450.ui.auth.signin.SignInFragmentDirections;
import edu.uw.tcss450.team8tcss450.utils.ColorTheme;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public SettingsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorTheme.onActivityCreateSetTheme(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = getActivity().getSharedPreferences("App Shared Prefs", Context.MODE_PRIVATE);

        // color switching
        binding.buttonAlttheme.setOnClickListener(button -> {
            prefs.edit().putString("Shared Prefs Theme", "Alt").apply();
            ColorTheme.changeToTheme(getActivity(), ColorTheme.THEME_ALT);
        });
        binding.buttonDeftheme.setOnClickListener(button -> {
            prefs.edit().putString("Shared Prefs Theme", "Default").apply();
            ColorTheme.changeToTheme(getActivity(), ColorTheme.THEME_DEFAULT);
        });

        binding.buttonChangepassword.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SettingsFragmentDirections.actionNavigationSettingsToChangePasswordFragment()
                ));

    }

}