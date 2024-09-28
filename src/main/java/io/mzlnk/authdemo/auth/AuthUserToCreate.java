package io.mzlnk.authdemo.auth;

import io.mzlnk.authdemo.user.User;

public record AuthUserToCreate(
        User.Id userId,
        AuthUser.Id authId,
        AuthProvider provider
) {


}
