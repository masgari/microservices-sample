package microservices.sample.discovery.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ISet;
import microservices.sample.discovery.PublishedServiceInfo;
import microservices.sample.discovery.ServiceRegistry;

import static microservices.sample.discovery.hazelcast.HazelcastBuilder.PUBLISHED_SERVICES;

/**
 * Simple Hazelcast-based implementation for ServiceDiscovery
 *
 * @author mamad
 * @since 17/03/15.
 */
public class HazelcastServiceRegistry implements ServiceRegistry {
    private final HazelcastInstance hazelcastInstance;

    public HazelcastServiceRegistry(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public <S> void register(PublishedServiceInfo<S> serviceInfo) {
        //when a service register itself, all other processes will be notified (thanks to Hazelcast)
        ISet<Object> set = hazelcastInstance.getSet(PUBLISHED_SERVICES);
        set.add(serviceInfo);
    }

    @Override
    public <S> void deRegister(PublishedServiceInfo<S> serviceInfo) {
        ISet<Object> set = hazelcastInstance.getSet(PUBLISHED_SERVICES);
        set.remove(serviceInfo);
    }

    @Override
    public void shutdown() {
        hazelcastInstance.shutdown();
    }

}
