package pl.db.impl.sql;

import org.springframework.stereotype.Component;
import pl.db.IUserRepository;
import pl.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class UserRepositorySQL implements IUserRepository {
    private final String GET_USER_BY_LOGIN_SQL = "select * from users where login = ?";
    private final String INSERT_USER_SQL = "INSERT INTO users (login, password) VALUES (?, ?)";

    @Override
    public Optional<User> getUser(String login) {
        try {
            PreparedStatement preparedStatement = Constants.CONNECTION.prepareStatement(GET_USER_BY_LOGIN_SQL);
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve user by login: " + login);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(User user) {
        try (PreparedStatement stmt = Constants.CONNECTION.prepareStatement(INSERT_USER_SQL)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Failed to save user " + user.getLogin());
            return false;
        }
    }
}
