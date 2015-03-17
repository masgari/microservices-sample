package microservices.sample.discovery.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.ItemListenerConfig;
import com.hazelcast.config.SetConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ItemListener;

import java.util.UUID;

/**
 * Builder class for creating a configured Hazelcast instance.
 *
 * @author mamad
 * @since 17/03/15.
 */
public class HazelcastBuilder {
    public static final String PUBLISHED_SERVICES = "_published_services";

    private ItemListener listener;

    public static HazelcastBuilder create() {
        return new HazelcastBuilder();
    }

    public HazelcastBuilder withListener(ItemListener listener) {
        this.listener = listener;
        return this;
    }

    public HazelcastInstance build() {
        Config config = new Config();
        SetConfig setConfig = new SetConfig();
        setConfig.setName(PUBLISHED_SERVICES);
        setConfig.addItemListenerConfig(new ItemListenerConfig(listener, true));
        config.addSetConfig(setConfig);
        config.setInstanceName(UUID.randomUUID().toString());

        return Hazelcast.newHazelcastInstance(config);
    }
}
