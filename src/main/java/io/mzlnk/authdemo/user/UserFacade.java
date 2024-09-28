package io.mzlnk.authdemo.user;

import java.util.Optional;

public class UserFacade {

    private final UserRepository userRepository;

    public UserFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserToCreate userToCreate) {
        return userRepository.createUser(userToCreate);
    }

    public Optional<User> findUser(User.Id id) {
        return userRepository.findUser(id);
    }

}
