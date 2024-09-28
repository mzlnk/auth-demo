package io.mzlnk.authdemo.auth;

import io.mzlnk.authdemo.user.User;

public record AuthUser(
        User.Id userId,
        AuthProvider provider,
        AuthUser.Id authId
) {

    public record Id(String value) {

        @Override
        public String toString() {
            return value;
        }
    }

}
