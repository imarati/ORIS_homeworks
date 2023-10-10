package repositories;

import models.User;

public interface UserRepository extends CrudRepository<User>{
    void findBylogin(String login, String password);
    boolean findByUuid(String uuid);
}