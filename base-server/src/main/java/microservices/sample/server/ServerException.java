package microservices.sample.server;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class ServerException extends Exception {
    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
