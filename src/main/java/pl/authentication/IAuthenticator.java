package pl.authentication;

import pl.model.User;

public interface IAuthenticator {
    boolean authenticate(User user);
}
