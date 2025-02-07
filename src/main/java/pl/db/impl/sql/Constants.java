package pl.db.impl.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public interface Constants {
    Connection CONNECTION = connect();

    static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:shop.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
