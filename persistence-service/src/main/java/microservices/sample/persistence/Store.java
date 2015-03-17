package microservices.sample.persistence;

import microservices.sample.IdResponse;

import java.util.function.Consumer;

/**
 * @author mamad
 * @since 17/03/15.
 */
public interface Store {
    <T> void saveAsync(T object, Consumer<IdResponse> onSuccessCallback, Consumer<Throwable> onErrorCallback);

    void findById(String id, Consumer<Object> onSuccessCallback, Consumer<Throwable> onErrorCallback);
}
