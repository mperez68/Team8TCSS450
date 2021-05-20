/**
 * @author Marc Perez
 * @date today
 */

package edu.uw.tcss450.team8tcss450.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.uw.tcss450.team8tcss450.R;
import edu.uw.tcss450.team8tcss450.databinding.FragmentChangePasswordBinding;
import edu.uw.tcss450.team8tcss450.utils.PasswordValidator;

import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdUpperCase;

/**
 *
 */
public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding mBinding;
    private ChangePasswordViewModel mChangePasswordModel;

    /* A PasswordValidator object to validate the length of the password, absence of white space,
        contains a special character and digit, and if the passwords match. */
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(mBinding.textNewPassword2.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangePasswordModel = new ViewModelProvider(getActivity())
                .get(ChangePasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentChangePasswordBinding.inflate(inflater);

        return mBinding.getRoot();
    }

    /**
     * onViewCreated adds listeners to the front end elements and retrieves arguments
     * passed to the fragment.
     *
     * @param theView
     * @param theSavedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View theView, @Nullable Bundle theSavedInstanceState) {
        super.onViewCreated(theView, theSavedInstanceState);

        mBinding.buttonSettingsUpdate.setOnClickListener(this::attemptChangePassword);
        mChangePasswordModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptChangePassword(final View theButton){ validatePasswordsMatch(); }

    /**
     * Validates that the password and the confirmation input are matching.
     */
    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(mBinding.textNewPassword2.getText().toString().trim()));

        matchValidator.processResult(
                matchValidator.apply(mBinding.textNewPassword1.getText().toString().trim()),
                this::validatePassword,
                result -> mBinding.textNewPassword1.setError("Passwords must match."));
    }

    /**
     * Validates the password input.
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(mBinding.textNewPassword1.getText().toString()),
                this::verifyAuthWithServer,
                result -> mBinding.textNewPassword1.setError("Please enter a valid Password."));
    }

    /**
     * Verifies the registration with the web service.
     */
    private void verifyAuthWithServer() {
        mChangePasswordModel.connect(
                mBinding.editEmail.getText().toString(),
                mBinding.textOldPassword.getText().toString(),
                mBinding.textNewPassword1.getText().toString());
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    mBinding.editEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                getActivity().onBackPressed();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

}