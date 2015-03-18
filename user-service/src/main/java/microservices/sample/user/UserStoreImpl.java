package microservices.sample.user;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import microservices.sample.GenericResponse;
import microservices.sample.IdResponse;
import microservices.sample.persistence.PersistenceService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * This class is not implemented async.
 *
 *
 * @author mamad
 * @since 17/03/15.
 */
@Singleton
public class UserStoreImpl implements UserStore {

    private final PersistenceServiceProvider persistenceServiceProvider;

    @Inject
    public UserStoreImpl(PersistenceServiceProvider persistenceServiceProvider) {
        this.persistenceServiceProvider = persistenceServiceProvider;
    }

    @Override
    public void saveAsync(User user, Consumer<IdResponse> onSuccessCallback, Consumer<Throwable> onErrorCallback) {
        if (user == null) {
            onErrorCallback.accept(new NullPointerException("user can not be null."));
            return;
        }

        if (Strings.isNullOrEmpty(user.getId())) {
            user = new User(user.getName(), UUID.randomUUID().toString());
        }

        PersistenceService persistenceService = persistenceServiceProvider.get();
        IdResponse idResponse = persistenceService.save(user);
        onSuccessCallback.accept(IdResponse.of(idResponse.getId()));
    }

    @Override
    public void connectAsync(String userId, String secondUserId, Consumer<GenericResponse> onSuccessCallback, Consumer<Throwable> onErrorCallback) {
        PersistenceService persistenceService = persistenceServiceProvider.get();
        //todo: handle user not found (HTTP 404)
        User user = persistenceService.findById(userId);
        User secondUser = persistenceService.findById(secondUserId);
        user.addConnection(secondUser.getId());
        //save the user
        persistenceService.save(user);

        //and second user
        secondUser.addConnection(user.getId());
        persistenceService.save(secondUser);

        onSuccessCallback.accept(GenericResponse.of("connected."));
    }

    @Override
    public void listConnections(String userId, int max, Consumer<List<User>> onSuccessCallback, Consumer<Throwable> onErrorCallback) {
        PersistenceService persistenceService = persistenceServiceProvider.get();
        User user = persistenceService.findById(userId);
        List<String> connectionIds = user.getConnectionIds();
        if (connectionIds == null || connectionIds.isEmpty()) {
            onSuccessCallback.accept(Collections.emptyList());
            return;
        }
        List<User> connections = connectionIds.stream()
                .limit(max)
                .map(persistenceService::findById)
                .collect(Collectors.toList());

        onSuccessCallback.accept(connections);
    }
}
