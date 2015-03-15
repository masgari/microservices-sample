package microservices.sample.server;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import ratpack.server.ServerEnvironment;
import ratpack.server.internal.DefaultServerConfigBuilder;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class UserServer extends BaseServer {

    public UserServer(int port) throws ServerException {
        super(chain -> chain.prefix("v1", new UserHandlerFactory()).handler(ctx -> ctx.render("Users Service - version 1.0")),
                DefaultServerConfigBuilder
                        .noBaseDir(ServerEnvironment.env()).port(port).build(),
                spec -> spec.add(new UserModule()));
    }

    public static void main(String[] args) {
        Params params = new Params();
        JCommander commander = new JCommander(params);
        try {
            commander.parse(args);
            if (params.isHelp()) {
                commander.usage();
                return;
            }
        } catch (Exception e) {
            commander.usage();
            return;
        }

        try {
            UserServer server = new UserServer(params.getPort());
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Params {
        public static final int MIN_PORT = 2000;
        public static final int MAX_PORT = 4000;
        @Parameter(names = {"-h", "--help"}, description = "Display help message", help = true)
        private boolean help;

        @Parameter(names = {"-p", "--port"}, description = "Server listen port. Default value is in " +
                "range [" + MIN_PORT + "," + MAX_PORT + "]")
        private int port = AvailablePortProvider.between(MIN_PORT, MAX_PORT).nextPort();

        public boolean isHelp() {
            return help;
        }

        public int getPort() {
            return port;
        }
    }
}
