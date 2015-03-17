package microservices.sample.persistence;

import microservices.sample.IdResponse;

import java.util.function.Consumer;

/**
 * Hazelcast-based distributed in-memory store implementation.
 *
 * @author mamad
 * @since 17/03/15.
 */
public class HazelcastStore implements Store {
    @Override
    public <T> void saveAsync(T object, Consumer<IdResponse> onSuccessCallback, Consumer<Throwable> onErrorCallback) {
        onErrorCallback.accept(new RuntimeException("Not implemented yet."));
    }
}
