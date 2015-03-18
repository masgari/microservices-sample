package microservices.sample.user;

import microservices.sample.persistence.PersistenceService;

/**
 * @author mamad
 * @since 17/03/15.
 */
public interface PersistenceServiceProvider {
    PersistenceService get();
}
