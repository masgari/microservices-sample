package microservices.sample.server;

import org.junit.Test;

import static microservices.sample.server.NetUtils.MAX_PORT_NUMBER;
import static microservices.sample.server.NetUtils.MIN_PORT_NUMBER;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class AvailablePortProviderTest {
    @Test
    public void testInvalidRanges() throws Exception {
        try {
            AvailablePortProvider.between(0, 1);
            fail("Should not reach this line");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testNextAvailablePort() throws Exception {
        AvailablePortProvider portGenerator = new AvailablePortProvider(MIN_PORT_NUMBER, MAX_PORT_NUMBER, p -> p % 2 == 0);
        int nextPort = portGenerator.nextPort();
        assertTrue(nextPort >= MIN_PORT_NUMBER);
        assertTrue(nextPort <= MAX_PORT_NUMBER);
        assertTrue(nextPort % 2 == 0);
    }

    @Test
    public void testNoPortAvailable() throws Exception {
        AvailablePortProvider portGenerator = new AvailablePortProvider(MIN_PORT_NUMBER, MIN_PORT_NUMBER + 3, p -> p > MAX_PORT_NUMBER);
        try {
            portGenerator.nextPort();
            fail("Should not reach this line");
        } catch (Exception e) {
            //its okay
        }
    }
}
