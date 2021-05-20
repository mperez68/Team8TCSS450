package edu.uw.tcss450.team8tcss450.ui.settings;

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
public class SettingsFragment extends Fragment implements View.OnClickListener {

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

        binding.buttonSettingsChange.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SettingsFragmentDirections.actionNavigationSettingsToChangePasswordFragment()
                ));

        binding.testColorButton.setOnClickListener(button ->
                ColorTheme.changeToTheme(getActivity(), ColorTheme.THEME_2)
                );
        binding.testColorButton2.setOnClickListener(button ->
                ColorTheme.changeToTheme(getActivity(), ColorTheme.THEME_DEFAULT)
                );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testColorButton:
                ColorTheme.changeToTheme(getActivity(), ColorTheme.THEME_2);
                break;
            case R.id.testColorButton2:
                ColorTheme.changeToTheme(getActivity(), ColorTheme.THEME_DEFAULT);
                break;
        }
    }

}