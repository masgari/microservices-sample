package microservices.sample.persistence;

/**
 * Generic persistence service
 * @author mamad
 * @since 15/03/15.
 */
public interface PersistenceService<T> {
    /**
     * Save the entity.
     * @param entity The entity
     * @return id of the entity
     */
    String save(T entity);

    T findById(String id);
}
