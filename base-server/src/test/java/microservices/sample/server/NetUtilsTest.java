package microservices.sample.server;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author mamad
 * @since 16/03/15.
 */
public class NetUtilsTest {
    @Test
    public void testGetLocalIP() throws Exception {
        Map<String, String> ips = NetUtils.listIPs();
        Assert.assertNotNull(ips);
        Assert.assertFalse(ips.isEmpty());
    }
}
