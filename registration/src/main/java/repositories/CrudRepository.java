package repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(T entity);
}