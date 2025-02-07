package pl.authentication.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.authentication.IAuthenticator;
import pl.authentication.IPasswordHasher;
import pl.db.IUserRepository;
import pl.model.User;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class Authenticator implements IAuthenticator {
    private final IUserRepository userRepository;
    private final IPasswordHasher passwordHasher;

    @Override
    public boolean authenticate(User user) {
        Optional<User> userBox = this.userRepository.getUser(user.getLogin());
        return userBox.isPresent() &&
                userBox.get().getPassword()
                        .equals(this.passwordHasher.hashPassword(user.getPassword()));
    }
}
