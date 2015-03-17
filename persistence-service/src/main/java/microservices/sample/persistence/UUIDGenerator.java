package microservices.sample.persistence;

import com.google.inject.Singleton;

import java.util.UUID;

/**
 * @author mamad
 * @since 17/03/15.
 */
@Singleton
public class UUIDGenerator implements IdGenerator {
    @Override
    public String newId() {
        return UUID.randomUUID().toString();
    }
}
