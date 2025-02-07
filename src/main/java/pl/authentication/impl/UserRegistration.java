package pl.authentication.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.authentication.IPasswordHasher;
import pl.authentication.IUserRegistration;
import pl.db.IUserRepository;
import pl.model.User;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserRegistration implements IUserRegistration {
    private final IUserRepository userRepository;
    private final IPasswordHasher passwordHasher;

    @Override
    public boolean registerNewUser(String login, String password) {
        Optional<User> existingUser = this.userRepository.getUser(login);
        return existingUser.isEmpty() && this.userRepository.save(new User(login, this.passwordHasher.hashPassword(password)));
    }
}
