package hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import microservices.sample.discovery.PublishedServiceInfo;

import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author mamad
 * @since 16/03/15.
 */
public class HazelcastApp {
    private final HazelcastInstance hazelcastInstance;
    private final int id;

    public HazelcastApp(String name, int id) {
        this.id = id;
        Config config = new Config();
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("nodes");
        mapConfig.addEntryListenerConfig(new EntryListenerConfig(MapEntryListener.class.getCanonicalName(), false, true));
        config.addMapConfig(mapConfig);
        config.setInstanceName(name + id);
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        HazelcastApp app = new HazelcastApp("App", new Random().nextInt(10));
        app.doSomeWork();
        app.waitFor(20, TimeUnit.SECONDS);
        app.shutdown();
        System.out.println("total time:" + Duration.ofMillis(System.currentTimeMillis() - start));
    }

    private void doSomeWork() {
        IMap<Object, Object> nodes = hazelcastInstance.getMap("nodes");
        if (!nodes.containsKey("node" + id)) {
            nodes.put("node" + id, "Hello");
            nodes.put("info" + id, new PublishedServiceInfo<>(String.class, "ip", id, "0.1"));
        }
        for (Map.Entry<Object, Object> entry : nodes.entrySet()) {
            System.out.printf("%s = %s%n", entry.getKey(), entry.getValue());
        }
    }

    private void shutdown() {
        hazelcastInstance.shutdown();
    }

    private void waitFor(long amount, TimeUnit unit) throws InterruptedException {
        Thread.sleep(unit.toMillis(amount));
    }

}
