package repositories;

import models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

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

}
