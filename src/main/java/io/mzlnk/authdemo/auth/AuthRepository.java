package io.mzlnk.authdemo.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

interface AuthRepository {

    Optional<AuthUser> findByIdAndProvider(AuthUser.Id id, AuthProvider provider);

    AuthUser create(AuthUserToCreate userToCreate);

}

class InMemoryAuthRepository implements AuthRepository {

    private final Map<Key, AuthUser> authUsers = new HashMap<>();

    @Override
    public Optional<AuthUser> findByIdAndProvider(AuthUser.Id id, AuthProvider provider) {
        return Optional.ofNullable(authUsers.get(new Key(id, provider)));
    }

    @Override
    public AuthUser create(AuthUserToCreate userToCreate) {
        AuthUser authUser = new AuthUser(
                userToCreate.userId(),
                userToCreate.provider(),
                userToCreate.authId()
        );
        authUsers.put(new Key(authUser.authId(), authUser.provider()), authUser);
        return authUser;
    }

    private record Key(AuthUser.Id id, AuthProvider provider) { }

}

