package repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudRepository<T> {
    void save(T entity);
}