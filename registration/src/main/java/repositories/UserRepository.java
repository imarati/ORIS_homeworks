package repositories;

import models.User;

public interface UserRepository extends CrudRepository<User>{
    int findBylogin(String login, String password);
}