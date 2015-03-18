package microservices.sample.user;

import com.google.inject.AbstractModule;
import com.hazelcast.core.HazelcastInstance;
import microservices.sample.discovery.ServiceDiscovery;
import microservices.sample.discovery.ServiceDiscoveryImpl;
import microservices.sample.discovery.hazelcast.HazelcastBuilder;
import microservices.sample.discovery.hazelcast.PublishServiceListener;
import microservices.sample.user.ratpack.UserHandlerFactory;
import microservices.sample.user.ratpack.UsersCollectionHandler;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class UserModule extends AbstractModule {

    public static final String PERSISTENCE_SERVICE_VERSION = "v1";

    @Override
    protected void configure() {
        //build the discovery service
        ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl();
        bind(ServiceDiscovery.class).toInstance(serviceDiscovery);

        HazelcastInstance hazelcastInstance = HazelcastBuilder.create()
                .withListener(new PublishServiceListener(serviceDiscovery))
                .build();
        bind(HazelcastInstance.class).toInstance(hazelcastInstance);

        AutoDiscoveryPersistenceServiceProvider persistenceServiceProvider =
                new AutoDiscoveryPersistenceServiceProvider(PERSISTENCE_SERVICE_VERSION);
        //to notify persistenceServiceProvider whenever a new persistenceService is up and running
        serviceDiscovery.addListener(persistenceServiceProvider);

        bind(PersistenceServiceProvider.class).toInstance(persistenceServiceProvider);

        bind(UserStore.class).to(UserStoreImpl.class);

        //ratpack handlers
        bind(UsersCollectionHandler.class);
        bind(UserHandlerFactory.class);
    }
}
