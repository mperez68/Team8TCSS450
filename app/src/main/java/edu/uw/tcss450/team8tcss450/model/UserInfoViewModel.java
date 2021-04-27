package edu.uw.tcss450.team8tcss450.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.auth0.android.jwt.JWT;

public class UserInfoViewModel extends ViewModel {

    /**
     * Constant variable used for connection timeouts when pinging for JWT.
     */
    public static final int mLeeway = 5;
    private final JWT mJwt;

    private UserInfoViewModel(JWT jwt) {
        mJwt = jwt;
    }

    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final JWT jwt;

        public UserInfoViewModelFactory(JWT jwt) {
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }

    public JWT getJWT() {
        return this.mJwt;
    }

    /**
     * Asks if the JWT stored in this ViewModel is expired.
     *
     *
     * @return true if the JWT stored in this ViewModel is expired, false otherwise
     */
    public boolean isExpired() {
        return mJwt.isExpired(mLeeway);
    }

    /**
     * Get the email address that is stored in teh payload of the JWT this ViewModel holds.
     *
     * @return the email stored in the JWT this ViewModel holds
     * @throws IllegalStateException when the JWT stored in thsi ViewModel is expired (Will not
     *      happen in this lab)
     */
    public String getEmail() {
        if (!mJwt.isExpired(mLeeway)) {
            return mJwt.getClaim("email").asString();
        } else {
            throw new IllegalStateException("JWT is expired!");
        }
    }



}
