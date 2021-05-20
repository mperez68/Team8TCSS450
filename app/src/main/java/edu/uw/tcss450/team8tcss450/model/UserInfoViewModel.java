package edu.uw.tcss450.team8tcss450.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.auth0.android.jwt.JWT;

public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;

    private UserInfoViewModel(String email, String jwt) {
        mEmail = email;
        mJwt = jwt;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getmJwt() {
        return mJwt;
    }


    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;

        public UserInfoViewModelFactory(String email, String jwt) {
            this.email = email;
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }

//    /**
//     * Asks if the JWT stored in this ViewModel is expired.
//     *
//     * @return true if the JWT stored in this ViewModel is expired, false otherwise
//     */
//    public boolean isExpired() {
//        return mJwt.isExpired(mLeeway);
//    }

//    /**
//     * Get the email address that is stored in the payload of the JWT this ViewModel holds.
//     *
//     * @return the email stored in the JWT this ViewModel holds
//     * @throws IllegalStateException when the JWT stored in this ViewModel is expired (Will not
//     *      happen in this lab)
//     */
//    public String getEmail() {
//        if (!mJwt.isExpired(mLeeway)) {
//            return mJwt.getClaim("email").asString();
//        } else {
//            throw new IllegalStateException("JWT is expired!");
//        }
//    }
}
