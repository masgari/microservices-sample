package microservices.sample.discovery.hazelcast;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import microservices.sample.discovery.PublishedServiceInfo;
import microservices.sample.discovery.ServiceDiscovery;
import microservices.sample.discovery.ServiceDiscoveryListener;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class PublishServiceListener implements ItemListener {
    private final ServiceDiscovery serviceDiscovery;

    public PublishServiceListener(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public void itemAdded(ItemEvent event) {
        if (!(event.getItem() instanceof PublishedServiceInfo)) {
            return;
        }
        PublishedServiceInfo info = (PublishedServiceInfo) event.getItem();
        //notify listeners
        for (ServiceDiscoveryListener discoveryListener : serviceDiscovery.getListeners()) {
            discoveryListener.onServiceJoined(info);
        }
    }

    @Override
    public void itemRemoved(ItemEvent event) {
        if (!(event.getItem() instanceof PublishedServiceInfo)) {
            return;
        }
        PublishedServiceInfo info = (PublishedServiceInfo) event.getItem();
        for (ServiceDiscoveryListener discoveryListener : serviceDiscovery.getListeners()) {
            discoveryListener.onServiceLeft(info);
        }
    }
}
