package microservices.sample;

/**
 * @author Mohammad Asgari
 * @version 1.0
 * @since 18/03/15
 */
public class GenericResponse {

    private final String message;

    public GenericResponse(String message) {
        this.message = message;
    }

    public static GenericResponse of(String message) {
        return new GenericResponse(message);
    }

    public String getMessage() {
        return message;
    }
}
