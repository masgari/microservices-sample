package microservices.sample.base;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Map;

/**
 * Network related utility methods
 * <p>
 * Port related methods taken from:
 * http://svn.apache.org/viewvc/camel/trunk/components/camel-test/src/main/java/org/apache/camel/test/AvailablePortFinder.java?view=co
 *
 * @author mamad
 * @since 15/03/15.
 */
public final class NetUtils {
    /**
     * The minimum server currentMinPort number. Set at 1100 to avoid returning privileged
     * currentMinPort numbers.
     */
    public static final int MIN_PORT_NUMBER = 1100;
    /**
     * The maximum server currentMinPort number.
     */
    public static final int MAX_PORT_NUMBER = 49151;
    private static final Logger LOGGER = LoggerFactory.getLogger(NetUtils.class);

    private NetUtils() {
    }

    /**
     * Checks to see if a specific port is available.
     *
     * @param port the port number to check for availability
     * @return <tt>true</tt> if the port is available, or <tt>false</tt> if not
     * @throws IllegalArgumentException is thrown if the port number is out of range
     */
    public static boolean isAvailable(int port) throws IllegalArgumentException {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start currentMinPort: " + port + ", must be between (" +
                    MIN_PORT_NUMBER + ", " + MAX_PORT_NUMBER + ")");
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) { // NO SONAR
            // Do nothing
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) { // NO SONAR
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

    /**
     * List ip (v4) addresses for running interfaces, ignore local ip addresses.
     *
     * @return map of network interface name to IPv4
     */
    public static Map<String, String> listIPs() throws SocketException {
        Map<String, String> result = Maps.newHashMap();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (!networkInterface.isLoopback() && !networkInterface.isVirtual() && networkInterface.isUp()) {
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                InetAddress address = null;
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        address = inetAddress;
                        break;
                    }
                }
                if (address != null) {
                    result.put(networkInterface.getName(), address.getHostAddress());
                }
            }
        }
        return ImmutableMap.copyOf(result);
    }

    public static String guessIPAddress() {
        try {
            Map<String, String> localIPs = listIPs();
            for (Map.Entry<String, String> entry : localIPs.entrySet()) {
                String iface = entry.getKey().toLowerCase();
                if (iface.contains("eth") || iface.contains("wlan")) {
                    return entry.getValue();
                }
            }
            return "localhost";
        } catch (SocketException e) {
            LOGGER.error("Error in listing ip addresses", e);
            return "localhost";
        }
    }
}
