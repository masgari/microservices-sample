package microservices.sample.persistence.ratpack;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import microservices.sample.base.AvailablePortProvider;
import microservices.sample.base.ratpack.BaseModule;
import microservices.sample.base.ratpack.BaseServer;
import microservices.sample.base.ratpack.ServerException;
import microservices.sample.persistence.PersistenceModule;
import ratpack.server.ServerEnvironment;
import ratpack.server.internal.DefaultServerConfigBuilder;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class PersistenceServer extends BaseServer {

    public PersistenceServer(int port) throws ServerException {
        super(chain -> chain.prefix("v1", PersistenceHandlerFactory.class)
                        .handler(ctx -> ctx.render("Persistence Service - version 1.0")),
                //create ratpack config
                DefaultServerConfigBuilder.noBaseDir(ServerEnvironment.env()).port(port).build(),
                //add Guice modules
                spec -> spec.add(new BaseModule()).add(new PersistenceModule()));
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
            PersistenceServer server = new PersistenceServer(params.getPort());
            System.out.println("Listening on port:" + params.getPort());
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class Params {
        public static final int MIN_PORT = 4100;
        public static final int MAX_PORT = 4800;
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
