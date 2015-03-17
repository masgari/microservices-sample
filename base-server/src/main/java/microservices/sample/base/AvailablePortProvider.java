package microservices.sample.base;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class AvailablePortProvider {
    private final int from;
    private final int to;
    private final Function<Integer, Boolean> portChecker;

    public AvailablePortProvider(int from, int to, Function<Integer, Boolean> portChecker) {
        Preconditions.checkArgument(from >= NetUtils.MIN_PORT_NUMBER, "Min acceptable value for 'from':" + NetUtils.MIN_PORT_NUMBER);
        Preconditions.checkArgument(to <= NetUtils.MAX_PORT_NUMBER, "Max acceptable value for 'to':" + NetUtils.MAX_PORT_NUMBER);
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
