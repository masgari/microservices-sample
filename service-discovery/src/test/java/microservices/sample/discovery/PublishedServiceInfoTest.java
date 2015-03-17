package microservices.sample.discovery;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class PublishedServiceInfoTest {
    @Test
    public void testEquals() throws Exception {
        PublishedServiceInfo info1 = new PublishedServiceInfo<>(String.class, null, 0, null);
        assertTrue(info1.equals(info1));
        PublishedServiceInfo info2 = new PublishedServiceInfo<>(String.class, null, 0, null);
        assertTrue(info1.equals(info2));
        assertTrue(info2.equals(info1));
        PublishedServiceInfo info3 = new PublishedServiceInfo<>(String.class, null, 0, null);
        assertTrue(info1.equals(info3));

        PublishedServiceInfo info4 = new PublishedServiceInfo<>(String.class, null, 1, null);
        assertFalse(info1.equals(info4));

    }
}
