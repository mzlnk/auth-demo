package io.mzlnk.authdemo.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

interface UserRepository {

    User createUser(UserToCreate userToCreate);

    Optional<User> findUser(User.Id id);

}

class InMemoryUserRepository implements UserRepository {

    private final Map<User.Id, User> usersById = new HashMap<>();

    @Override
    public User createUser(UserToCreate userToCreate) {
        User user = new User(new User.Id(UUID.randomUUID().toString()));
        usersById.put(user.id(), user);
        return user;
    }

    @Override
    public Optional<User> findUser(User.Id id) {
        return Optional.ofNullable(usersById.get(id));
    }
}

