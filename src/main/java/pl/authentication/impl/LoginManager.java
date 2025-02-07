package pl.authentication.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.authentication.IAuthenticator;
import pl.authentication.ILoginManager;
import pl.authentication.IUserRegistration;
import pl.gui.IGUI;

@RequiredArgsConstructor
@Component
public class LoginManager implements ILoginManager {
    private final IAuthenticator authenticator;
    private final IUserRegistration userRegistration;
    private final IGUI gui;

    @Override
    public boolean login() {
        boolean success = this.authenticator.authenticate(this.gui.aksForCredentials());
        this.gui.showResultMessage(success ? "Login successful!" : "Invalid login or password.");
        return success;
    }

    @Override
    public void register() {
        boolean registrationSuccess = this.userRegistration.registerNewUser(this.gui.askForLogin(), this.gui.askForPassword());
        this.gui.showResultMessage(registrationSuccess ? "Registration successful!" : "Registration failed. The login may already be taken.");
    }
}
