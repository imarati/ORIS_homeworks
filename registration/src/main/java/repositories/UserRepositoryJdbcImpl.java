package repositories;

import models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserRepositoryJdbcImpl implements UserRepository{

    private Connection connection;
    private Statement statement;

    private static final String SQL_SELECT_ALL_FROM_USERS = "select * from users";
    private static final String SQL_INSERT_INTO_USERS = "INSERT INTO users (firstname, surname, login, pass) VALUES ";

    public UserRepositoryJdbcImpl(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    @Override
    public void save(User entity) {
        String sql = SQL_INSERT_INTO_USERS + "('" + entity.getFirstname() + "', '" + entity.getSurname() + "', '" + entity.getLogin() + "', '" + entity.getPassword() + "')";
        try {
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        System.out.println(entity.getFirstname() + " " + entity.getPassword() + " " + entity.getLogin() + " " + entity.getPassword());
    }

    @Override
    public int findBylogin(String login, String password) {
        String sql = "select * from users where login = '" + login + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet logintSet = statement.executeQuery(sql);
            logintSet.next();
            User user = User.builder()
                    .firstname(logintSet.getString("firstname"))
                    .surname(logintSet.getString("surname"))
                    .login(logintSet.getString("login"))
                    .password(logintSet.getString("pass"))
                    .build();

            System.out.println(user.getPassword().equals(password));
            return logintSet.getInt("id");
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
