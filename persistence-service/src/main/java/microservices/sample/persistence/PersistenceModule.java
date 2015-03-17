package microservices.sample.persistence;

import com.google.inject.AbstractModule;
import com.hazelcast.core.HazelcastInstance;
import microservices.sample.discovery.hazelcast.HazelcastBuilder;
import microservices.sample.persistence.ratpack.EntitiesChainHandler;
import microservices.sample.persistence.ratpack.PersistenceHandlerFactory;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class PersistenceModule extends AbstractModule {
    @Override
    protected void configure() {
        //ratpack handlers
        bind(PersistenceHandlerFactory.class);
        bind(EntitiesChainHandler.class);

        bind(IdGenerator.class).to(UUIDGenerator.class);
        bind(HazelcastInstance.class).toInstance(HazelcastBuilder.create().build());
        bind(Store.class).to(HazelcastStore.class);
    }
}
