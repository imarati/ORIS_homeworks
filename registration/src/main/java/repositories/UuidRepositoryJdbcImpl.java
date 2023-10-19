package repositories;

import models.User;
import models.UserUuid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UuidRepositoryJdbcImpl implements UuidRepository{
    private Connection connection;
    private Statement statement;

    private static final String SQL_SELECT_ALL_FROM_USERS_UUID = "select * from users_uuid";
    private static final String SQL_INSERT_INTO_USERS_UUID = "INSERT INTO users_uuid (uuid, user_id) VALUES ";

    public UuidRepositoryJdbcImpl(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    @Override
    public boolean findByUuid(String uuid) {
        String sql = "SELECT * FROM users_uuid WHERE uuid = '" + uuid + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet uuidSet = statement.executeQuery(sql);
            if(uuidSet.next()) {
                UserUuid userUuid = UserUuid.builder()
                        .uuid(uuidSet.getString("uuid"))
                        .userId(Integer.parseInt(uuidSet.getString("user_id")))
                        .build();


                String sqlUsers = "SELECT * FROM users WHERE id = " + userUuid.getUserId();
                ResultSet usersSet = statement.executeQuery(sqlUsers);

                if (usersSet.next()) {
                    return true;
                }
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        return false;

    }

    @Override
    public void save(UserUuid entity) {
        String sql = SQL_INSERT_INTO_USERS_UUID + "('" + entity.getUuid() + "', " + entity.getUserId() + ")";
        try {
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        System.out.println(entity.getUuid() + " " + entity.getUserId());

    }
}
