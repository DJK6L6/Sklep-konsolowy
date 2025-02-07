package pl.db;

import pl.model.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> getUser(String login);

    boolean save(User user);
}
