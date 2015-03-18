package microservices.sample;

import java.io.Serializable;

/**
 * Any object with a string id.
 *
 * @author mamad
 * @since 18/03/15.
 */
public interface Entity extends Serializable {
    String getId();
}
