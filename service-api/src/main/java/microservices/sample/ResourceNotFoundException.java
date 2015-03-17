package microservices.sample;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class ResourceNotFoundException extends Exception {
    private final String id;

    public ResourceNotFoundException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
