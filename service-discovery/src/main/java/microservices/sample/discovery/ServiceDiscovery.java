package microservices.sample.discovery;

import java.util.Set;

/**
 * This class provides method for finding available microservices.
 * Can be implemented using ZooKeeper or Etcd or other discovery services.
 * Simple implementation is provided using Hazelcast.
 *
 * @author mamad
 * @since 15/03/15.
 */
public interface ServiceDiscovery {
    void addListener(ServiceDiscoveryListener listener);

    void removeListener(ServiceDiscoveryListener listener);

    Set<ServiceDiscoveryListener> getListeners();
}
