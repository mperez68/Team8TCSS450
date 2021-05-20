/**
 * Fragment that allows the user to reset their password via email address.
 *
 * @author Marc Perez
 * @date 16 May 2021
 */

package edu.uw.tcss450.team8tcss450.ui.auth.reset;

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
import edu.uw.tcss450.team8tcss450.databinding.FragmentResetBinding;
import edu.uw.tcss450.team8tcss450.ui.auth.signin.SignInViewModel;
import edu.uw.tcss450.team8tcss450.utils.PasswordValidator;

import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdSpecialChar;

/**
 *
 */
public class ResetFragment extends Fragment {

    private FragmentResetBinding myBinding;
    private ResetViewModel mResetModel;

    private PasswordValidator myEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    public ResetFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate that binds the sign in view model.
     *
     * @param theSavedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle theSavedInstanceState) {
        super.onCreate(theSavedInstanceState);
        mResetModel = new ViewModelProvider(getActivity())
                .get(ResetViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myBinding = FragmentResetBinding.inflate(inflater);
        return myBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View theView, @Nullable Bundle theSavedInstanceState) {
        super.onViewCreated(theView, theSavedInstanceState);

        myBinding.buttonReset.setOnClickListener(this::attemptReset);

        mResetModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);
    }

    /**
     * Starter method for validation for reset.
     *
     * @param theButton
     *
     */
    private void attemptReset(final View theButton) {
        validateEmail();
    }

    /**
     * Client validation for email
     */
    private void validateEmail() {
        myEmailValidator.processResult(
                myEmailValidator.apply(myBinding.editResetEmail.getText().toString().trim()),
                this::verifyAuthWithServer,
                result -> myBinding.editResetEmail.setError("Please enter a valid Email address."));
    }

    /**
     * verify credentials with server.
     */
    private void verifyAuthWithServer() {
        mResetModel.connect(
                myBinding.editResetEmail.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     */
    private void navigateToSuccess() {
        Navigation.findNavController(getView())
                .navigate(ResetFragmentDirections.actionResetFragmentToSignInFragment());
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param theResponse the Response from the server
     */
    private void observeResponse(final JSONObject theResponse) {
        if (theResponse.length() > 0) {
            if (theResponse.has("code")) {
                try {
                    myBinding.editResetEmail.setError(
                            "Error Authenticating: " +
                                    theResponse.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToSuccess();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }


    }
}