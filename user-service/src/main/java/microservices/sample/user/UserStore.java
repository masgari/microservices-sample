package microservices.sample.user;

import microservices.sample.IdResponse;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author mamad
 * @since 17/03/15.
 */
public interface UserStore {
    /**
     * Save or update the user asynchronously
     *
     * @param user
     * @param onSuccessCallback
     * @param onErrorCallback
     */
    void saveAsync(User user, Consumer<IdResponse> onSuccessCallback, Consumer<Throwable> onErrorCallback);

    /**
     * Make connection between two users asynchronously, return the userId
     *
     * @param userId
     * @param secondUserId
     * @param onSuccessCallback
     * @param onErrorCallback
     */
    void connectAsync(String userId, String secondUserId, Consumer<IdResponse> onSuccessCallback, Consumer<Throwable> onErrorCallback);

    /**
     * Return list of users connected to the user with userId
     *
     * @param userId
     * @param max
     * @param onSuccessCallback
     * @param onErrorCallback
     */
    void listConnections(String userId, int max, Consumer<List<User>> onSuccessCallback, Consumer<Throwable> onErrorCallback);
}
