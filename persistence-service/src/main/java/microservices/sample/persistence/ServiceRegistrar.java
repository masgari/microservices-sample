package microservices.sample.persistence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import microservices.sample.discovery.PublishedServiceInfo;
import microservices.sample.discovery.ServiceRegistry;

/**
 * This class is responsible for registering PersistenceService info into ServiceRegistry.
 *
 * @author mamad
 * @since 17/03/15.
 */
@Singleton
public class ServiceRegistrar {
    private final PublishedServiceInfo<PersistenceService> info;

    private final ServiceRegistry registry;

    @Inject
    public ServiceRegistrar(String ipAddress, int port, ServiceRegistry registry, String version) {
        this.registry = registry;
        this.info = PublishedServiceInfo.of(PersistenceService.class, ipAddress, port, version);
    }

    public void register() {
        registry.register(info);
    }

}
