package microservices.sample.server;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static microservices.sample.server.NetUtils.MAX_PORT_NUMBER;
import static microservices.sample.server.NetUtils.MIN_PORT_NUMBER;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class AvailablePortProvider {
    private final int from;
    private final int to;
    private final Function<Integer, Boolean> portChecker;

    public AvailablePortProvider(int from, int to, Function<Integer, Boolean> portChecker) {
        checkArgument(from >= MIN_PORT_NUMBER, "Min acceptable value for 'from':" + MIN_PORT_NUMBER);
        checkArgument(to <= MAX_PORT_NUMBER, "Max acceptable value for 'to':" + MAX_PORT_NUMBER);
        checkArgument(to > from);
        this.from = from;
        this.to = to;
        this.portChecker = checkNotNull(portChecker, "Port checker function can not be null.");
    }

    public static AvailablePortProvider between(int from, int to) {
        return new AvailablePortProvider(from, to, NetUtils::isAvailable);
    }

    public int nextPort() {
        Set<Integer> notAvailable = Sets.newHashSet();
        int port = from;
        while (port <= to) {
            if (!notAvailable.contains(port) && portChecker.apply(port)) {
                return port;
            } else {
                notAvailable.add(port);
            }
            port++;
        }
        throw new RuntimeException("Could not find an open port in range:(" + from + "," + to + ")");
    }
}
