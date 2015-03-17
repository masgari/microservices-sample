package microservices.sample.persistence;

import com.google.inject.AbstractModule;
import microservices.sample.persistence.ratpack.PersistenceHandlerFactory;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class PersistenceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PersistenceHandlerFactory.class);
        bind(Store.class).to(HazelcastStore.class);
    }
}
