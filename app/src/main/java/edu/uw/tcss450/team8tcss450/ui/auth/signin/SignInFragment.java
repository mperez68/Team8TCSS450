package edu.uw.tcss450.team8tcss450.ui.auth.signin;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.team8tcss450.databinding.FragmentSignInBinding;
import edu.uw.tcss450.team8tcss450.model.PushyTokenViewModel;
import edu.uw.tcss450.team8tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.team8tcss450.utils.PasswordValidator;

import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.team8tcss450.utils.PasswordValidator.checkPwdSpecialChar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private FragmentSignInBinding myBinding;
    private SignInViewModel mySignInModel;

    private PushyTokenViewModel mPushyTokenViewModel;
    private UserInfoViewModel mUserViewModel;


    private PasswordValidator myEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator myPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());

    private SharedPreferences mySharedPrefs;
    public static final String sharedPrefKey = "Shared Prefs App";
    public static final String sharedPrefJwt = "Shared Prefs JWT";

    /**
     * Empty constructor
     *
     */
    public SignInFragment() {
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
        mySharedPrefs = getActivity().getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
        mySignInModel = new ViewModelProvider(getActivity())
                .get(SignInViewModel.class);

        mPushyTokenViewModel = new ViewModelProvider(getActivity())
                .get(PushyTokenViewModel.class);
    }

    /**
     * onCreate view that inflates myBinding.
     *
     * @param theInflater
     * @param theContainer
     * @param theSavedInstanceState
     * @return the root of the inflated binding.
     */
    @Override
    public View onCreateView(LayoutInflater theInflater, ViewGroup theContainer,
                             Bundle theSavedInstanceState) {
        // Inflate the layout for this fragment
        myBinding = FragmentSignInBinding.inflate(theInflater);

        return myBinding.getRoot();
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

        myBinding.buttonRegister.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
                ));

        myBinding.buttonReset.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SignInFragmentDirections.actionSignInFragmentToResetFragment()
                ));

        myBinding.buttonSignin.setOnClickListener(this::attemptSignIn);

        mySignInModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);

        //don't allow sign in until pushy token retrieved
        mPushyTokenViewModel.addTokenObserver(getViewLifecycleOwner(), token ->
                myBinding.buttonSignin.setEnabled(!token.isEmpty()));

        mPushyTokenViewModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observePushyPutResponse);

        SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments());
        myBinding.editEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        myBinding.editPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());
    }

    /**
     * Starter method for validation for sign in.
     *
     * @param theButton
     *
     */
    private void attemptSignIn(final View theButton) {
        validateEmail();
    }

    /**
     * Client validation for email
     */
    private void validateEmail() {
        myEmailValidator.processResult(
                myEmailValidator.apply(myBinding.editEmail.getText().toString().trim()),
                this::validatePassword,
                result -> myBinding.editEmail.setError("Please enter a valid Email address."));
    }

    /**
     * Client validation for password
     */
    private void validatePassword() {
        myPassWordValidator.processResult(
                myPassWordValidator.apply(myBinding.editPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> myBinding.editPassword.setError("Please enter a valid Password."));
    }

    /**
     * verify credentials with server.
     */
    private void verifyAuthWithServer() {
        mySignInModel.connect(
                myBinding.editEmail.getText().toString(),
                myBinding.editPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().


    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     * @param theEmail users email
     * @param theJwt the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String theEmail, final String theJwt) {

        if (myBinding.switchSignin.isChecked()) {
            mySharedPrefs.edit().putString(sharedPrefJwt, theJwt).apply();
        }

        Navigation.findNavController(getView())
                .navigate(SignInFragmentDirections.actionSignInFragmentToMainActivity(theEmail, theJwt));
        getActivity().finish();
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
                    myBinding.editEmail.setError(
                            "Error Authenticating: " +
                                    theResponse.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                try {
                    mUserViewModel = new ViewModelProvider(getActivity(),
                            new UserInfoViewModel.UserInfoViewModelFactory(
                                    myBinding.editEmail.getText().toString(),
                                    theResponse.getString("token")
                            )).get(UserInfoViewModel.class);

                    sendPushyToken();

                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }


    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to PushyTokenViewModel.
     *
     * @param response the Response from the server
     */
    private void observePushyPutResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                //this error cannot be fixed by the user changing credentials...
                myBinding.editEmail.setError(
                        "Error Authenticating on Push Token. Please contact support");
            } else {
                navigateToSuccess(
                        myBinding.editEmail.getText().toString(),
                        mUserViewModel.getmJwt()
                );
            }
        }
    }


    /**
     * Helper to abstract the request to send the pushy token to the web service
     */
    private void sendPushyToken() {
        mPushyTokenViewModel.sendTokenToWebservice(mUserViewModel.getmJwt());
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mySharedPrefs.contains(sharedPrefJwt)) {
            String token = mySharedPrefs.getString(sharedPrefJwt, "");
            JWT jwt = new JWT(token);
            if (!jwt.isExpired(0)) {
                // comment out the code below to disable automatic sign in
                String email = jwt.getClaim("email").asString();
                navigateToSuccess(email, token);
                return;
            }
        }
    }

}