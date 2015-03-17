package microservices.sample.discovery.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.ItemListenerConfig;
import com.hazelcast.config.SetConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ISet;
import com.hazelcast.core.ItemListener;
import microservices.sample.discovery.PublishedServiceInfo;
import microservices.sample.discovery.ServiceRegistry;

import java.util.UUID;

/**
 * Simple Hazelcast-based implementation for ServiceDiscovery
 *
 * @author mamad
 * @since 17/03/15.
 */
public class HazelcastServiceRegistry implements ServiceRegistry {
    public static final String PUBLISHED_SERVICES = "_published_services";
    private final HazelcastInstance hazelcastInstance;

    private HazelcastServiceRegistry(ItemListener listener) {
        Config config = new Config();
        SetConfig setConfig = new SetConfig();
        setConfig.setName(PUBLISHED_SERVICES);
        setConfig.addItemListenerConfig(new ItemListenerConfig(listener, true));
        config.addSetConfig(setConfig);
        config.setInstanceName(UUID.randomUUID().toString());

        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true).addMember("127.0.0.1");
        config.getNetworkConfig().getInterfaces().setEnabled(true).addInterface("127.0.0.*");

        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    public static ServiceRegistry newInstance(ItemListener listener) {
        return new HazelcastServiceRegistry(listener);
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
