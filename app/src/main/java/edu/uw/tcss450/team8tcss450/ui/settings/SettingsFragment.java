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

import edu.uw.tcss450.team8tcss450.databinding.FragmentSettingsBinding;
import edu.uw.tcss450.team8tcss450.utils.ColorTheme;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    public static final String sharedPrefKey = "Shared Prefs App";
    public static final String sharedPrefTheme = "Shared Prefs Theme";

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

        SharedPreferences prefs = getActivity().getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);

        // color switching
        binding.buttonApplytheme.setOnClickListener(button -> {
            if (binding.radioAlttheme.isChecked()) {
                prefs.edit().putString(sharedPrefTheme, "Alt").apply();
                ColorTheme.changeToTheme(getActivity(), ColorTheme.THEME_ALT);
            }
            else if (binding.radioDeftheme.isChecked()) {
                prefs.edit().putString(sharedPrefTheme, "Default").apply();
                ColorTheme.changeToTheme(getActivity(), ColorTheme.THEME_DEFAULT);
            }
            // Do nothing when no theme is selected
            else {
                //binding.buttonApplytheme.setError("No theme selected");
            }
        });

        binding.buttonChangepassword.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SettingsFragmentDirections.actionNavigationSettingsToChangePasswordFragment()
                ));

    }

}