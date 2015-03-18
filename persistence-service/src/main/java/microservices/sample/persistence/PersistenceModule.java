package microservices.sample.persistence;

import com.google.inject.AbstractModule;
import com.hazelcast.core.HazelcastInstance;
import microservices.sample.discovery.ServiceRegistry;
import microservices.sample.discovery.hazelcast.HazelcastBuilder;
import microservices.sample.discovery.hazelcast.HazelcastServiceRegistry;
import microservices.sample.persistence.ratpack.EntitiesCollectionHandler;
import microservices.sample.persistence.ratpack.PersistenceHandlerFactory;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class PersistenceModule extends AbstractModule {
    private final String ipAddress;
    private final int port;
    private final String version;

    public PersistenceModule(String ipAddress, int port, String version) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.version = version;
    }

    @Override
    protected void configure() {
        //ratpack handlers
        bind(PersistenceHandlerFactory.class);
        bind(EntitiesCollectionHandler.class);

        HazelcastInstance hazelcastInstance = HazelcastBuilder.create().build();

        ServiceRegistry serviceRegistry = new HazelcastServiceRegistry(hazelcastInstance);
        bind(ServiceRegistry.class).toInstance(serviceRegistry);

        bind(HazelcastInstance.class).toInstance(hazelcastInstance);
        bind(EntityStore.class).to(HazelcastEntityStore.class);

        ServiceRegistrar serviceRegistrar = new ServiceRegistrar(ipAddress, port, serviceRegistry, version);
        //now publish service into into registry
        serviceRegistrar.register();
    }
}
