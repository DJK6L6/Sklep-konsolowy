package pl.authentication.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import pl.authentication.IPasswordHasher;

@RequiredArgsConstructor
@Component
public class PasswordHasher implements IPasswordHasher {
    private final String seed = "aqLYx9jyn8xgGi0zf6mx1J3huK2InZ2f";

    @Override
    public String hashPassword(String password) {
        return DigestUtils.md5Hex(password + seed);
    }
}
