package microservices.sample.persistence;

import microservices.sample.IdResponse;

import java.util.function.Consumer;

/**
 * @author mamad
 * @since 17/03/15.
 */
public interface EntityStore {
    <T> void saveAsync(T object, Consumer<IdResponse> onSuccessCallback, Consumer<Throwable> onErrorCallback);

    <T> void findById(String id, Consumer<T> onSuccessCallback, Consumer<Throwable> onErrorCallback);
}
