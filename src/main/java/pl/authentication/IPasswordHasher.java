package pl.authentication;

public interface IPasswordHasher {
    String hashPassword(String password);
}

