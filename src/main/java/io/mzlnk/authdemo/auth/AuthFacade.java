package io.mzlnk.authdemo.auth;

import io.mzlnk.authdemo.user.User;
import io.mzlnk.authdemo.user.UserFacade;
import io.mzlnk.authdemo.user.UserToCreate;

import java.util.Optional;

public class AuthFacade {

    private final UserFacade userFacade;
    private final AuthRepository authRepository;
    private final TokenGenerator tokenGenerator;
    private final TokenVerifier tokenVerifier;

    AuthFacade(
            UserFacade userFacade,
            AuthRepository authRepository,
            TokenGenerator tokenGenerator,
            TokenVerifier tokenVerifier
    ) {
        this.userFacade = userFacade;
        this.authRepository = authRepository;
        this.tokenGenerator = tokenGenerator;
        this.tokenVerifier = tokenVerifier;
    }

    public Token generateToken(AuthUser.Id userId, AuthProvider provider) {
        var authUser = authRepository.findByIdAndProvider(
                /* id = */ userId,
                /* provider= */ provider
        ).orElseGet(() -> {
            var user = userFacade.createUser(new UserToCreate());
            var authUserToCreate = new AuthUserToCreate(
                    /* userId = */ user.id(),
                    /* authId = */ new AuthUser.Id(""),
                    /* provider = */ provider
            );
            return authRepository.create(authUserToCreate);
        });

        return tokenGenerator.generateToken(authUser);
    }

    public boolean verifyToken(Token token) {
        return tokenVerifier.verifyToken(token);
    }

}
