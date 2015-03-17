package microservices.sample.persistence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import microservices.sample.IdResponse;
import microservices.sample.ResourceNotFoundException;

import java.util.function.Consumer;

/**
 * Hazelcast-based distributed in-memory store implementation.
 *
 * @author mamad
 * @since 17/03/15.
 */
@Singleton
public class HazelcastStore implements Store {
    public static final String TABLE1 = "common";

    private final HazelcastInstance hazelcastInstance;
    private final IdGenerator idGenerator;

    @Inject
    public HazelcastStore(HazelcastInstance hazelcastInstance, IdGenerator idGenerator) {
        this.hazelcastInstance = hazelcastInstance;
        this.idGenerator = idGenerator;
    }

    @Override
    public <T> void saveAsync(T object, Consumer<IdResponse> onSuccessCallback, Consumer<Throwable> onErrorCallback) {
        if (object == null) {
            onErrorCallback.accept(new NullPointerException("Object can not be null."));
            return;
        }
        try {
            String id = idGenerator.newId();
            IMap<Object, Object> map = getMap();
            map.put(id, object);
            onSuccessCallback.accept(IdResponse.of(id));
        } catch (Exception e) {
            onErrorCallback.accept(e);
        }
    }

    private IMap<Object, Object> getMap() {
        return hazelcastInstance.getMap(TABLE1);
    }

    @Override
    public void findById(String id, Consumer<Object> onSuccessCallback, Consumer<Throwable> onErrorCallback) {
        if (id == null) {
            onErrorCallback.accept(new NullPointerException("id can not be null."));
            return;
        }

        IMap<Object, Object> map = getMap();
        if (map.containsKey(id)) {
            onSuccessCallback.accept(map.get(id));
        } else {
            onErrorCallback.accept(new ResourceNotFoundException(id));
        }
    }
}
