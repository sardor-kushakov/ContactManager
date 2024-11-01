package sarik.dev.util;

import sarik.dev.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    public static void createTable() {
        String sql = "create table if not exists contacts ("
                + "id bigserial primary key, "
                + "name varchar(50) not null, "
                + "surname varchar(50), "
                + "phone varchar(13) unique not null" +
                ")";
        try {
            Connection connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
