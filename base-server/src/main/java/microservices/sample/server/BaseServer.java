package microservices.sample.server;

import ratpack.func.Action;
import ratpack.guice.BindingsSpec;
import ratpack.guice.Guice;
import ratpack.handling.Chain;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class BaseServer {
    private final RatpackServer ratpackServer;

    public BaseServer(Action<Chain> handlers, ServerConfig config, Action<BindingsSpec> binding) throws ServerException {
        try {
            this.ratpackServer = RatpackServer.of(def ->
                    def
                            .serverConfig(config)
                            .registry(Guice.registry(binding))
                            .handlers(handlers));
        } catch (Exception e) {
            throw new ServerException("Error in building ratpack server.", e);
        }
    }

    public void start() throws ServerException {
        try {
            ratpackServer.start();
        } catch (Exception e) {
            throw new ServerException("Error in starting server.", e);
        }
    }

    public void stop() throws ServerException {
        try {
            ratpackServer.stop();
        } catch (Exception e) {
            throw new ServerException("Error in stopping server.", e);
        }
    }
}
