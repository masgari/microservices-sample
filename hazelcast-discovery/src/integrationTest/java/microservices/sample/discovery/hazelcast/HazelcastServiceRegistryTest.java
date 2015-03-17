package microservices.sample.discovery.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import microservices.sample.discovery.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class HazelcastServiceRegistryTest {
    public static final String IP = "ip1";
    public static final int PORT = 1;
    public static final String VERSION = "v1";
    //running two instances of service registry
    ServiceRegistry registry1;
    ServiceRegistry registry2;
    ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl();

    @Before
    public void setUp() throws Exception {
        HazelcastInstance instance1 = HazelcastBuilder.create().withListener(new PublishServiceListener(serviceDiscovery)).build();
        registry1 = new HazelcastServiceRegistry(instance1);

        HazelcastInstance instance2 = HazelcastBuilder.create().withListener(new PublishServiceListener(serviceDiscovery)).build();
        registry2 = new HazelcastServiceRegistry(instance2);
    }

    @After
    public void tearDown() throws Exception {
        registry1.shutdown();
        registry2.shutdown();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testJoinLeave() throws Exception {

        ServiceDiscoveryListener listener1 = mock(ServiceDiscoveryListener.class);
        doAnswer(invocation -> {
                    Object arg = invocation.getArguments()[0];
                    assertTrue(arg instanceof PublishedServiceInfo);
                    PublishedServiceInfo info = (PublishedServiceInfo) arg;
                    assertThat(info.getServiceClass(), equalTo(String.class));
                    assertThat(info.getIp(), equalTo(IP));
                    assertThat(info.getPort(), equalTo(PORT));
                    assertThat(info.getVersion(), equalTo(VERSION));
                    return null;
                }
        ).when(listener1).onServiceJoined(any(PublishedServiceInfo.class));
        serviceDiscovery.addListener(listener1);

        ServiceDiscoveryListener listener2 = mock(ServiceDiscoveryListener.class);
        serviceDiscovery.addListener(listener2);

        PublishedServiceInfo<String> serviceA = PublishedServiceInfo.of(String.class, IP, PORT, VERSION);
        registry1.register(serviceA);

        //give Hazelcast a little time to detect and notify discovery service
        sleepFor(1, SECONDS);

        verify(listener1, times(2)).onServiceJoined(any(PublishedServiceInfo.class));
        verify(listener2, times(2)).onServiceJoined(any(PublishedServiceInfo.class));

        //now leave the cluster
        registry1.deRegister(serviceA);

        sleepFor(1, SECONDS);

        verify(listener1, times(2)).onServiceLeft(any(PublishedServiceInfo.class));
        verify(listener2, times(2)).onServiceLeft(any(PublishedServiceInfo.class));
    }

    private void sleepFor(long amount, TimeUnit unit) throws InterruptedException {
        Thread.sleep(unit.toMillis(amount));
    }
}
