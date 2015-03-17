package microservices.sample;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class IdResponse {
    private final String id;

    public IdResponse(String id) {
        this.id = id;
    }

    public static IdResponse of(String id) {
        if (id == null || id.length() < 1) {
            throw new IllegalArgumentException("Invalid id:" + id);
        }
        return new IdResponse(id);
    }

    public String getId() {
        return id;
    }
}
