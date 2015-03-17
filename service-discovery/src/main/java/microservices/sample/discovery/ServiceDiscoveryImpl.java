package microservices.sample.discovery;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery {
    private final Set<ServiceDiscoveryListener> discoveryListeners = Sets.newConcurrentHashSet();

    @Override
    public void addListener(ServiceDiscoveryListener listener) {
        discoveryListeners.add(listener);
    }

    @Override
    public void removeListener(ServiceDiscoveryListener listener) {
        discoveryListeners.remove(listener);
    }

    @Override
    public Set<ServiceDiscoveryListener> getListeners() {
        return discoveryListeners;
    }
}
