package repositories;

import models.UserUuid;

public interface UuidRepository extends CrudRepository<UserUuid>{
    boolean findByUuid(String uuid);
}
