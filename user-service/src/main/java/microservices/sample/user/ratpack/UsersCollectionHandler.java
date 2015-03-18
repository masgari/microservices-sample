package microservices.sample.user.ratpack;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import microservices.sample.InvalidValueException;
import microservices.sample.base.ratpack.HandlerHelper;
import microservices.sample.user.User;
import microservices.sample.user.UserStore;
import ratpack.exec.Fulfiller;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import ratpack.util.MultiValueMap;

/**
 * @author mamad
 * @since 17/03/15.
 */
@Singleton
public class UsersCollectionHandler implements Action<Chain> {
    //rest collection name
    public static final String COLLECTION_NAME = "users";
    public static final String USER_PATH_TOKEN = "user";
    public static final String CONNECT_TO_QUERY_PARAM = "to";

    private final UserStore store;
    private final HandlerHelper helper;

    @Inject
    public UsersCollectionHandler(UserStore store, HandlerHelper helper) {
        this.store = store;
        this.helper = helper;
    }

    @Override
    public void execute(Chain chain) throws Exception {
        chain
                //connections is a server side collection
                .handler(":user/connections", this::handleGetUserConnectionsListAsync)
                        //connect is a controller for resource user, another user id is passed as query parameter 'to'
                        //e.g. POST /v1/users/<user-id>/connect?to=<another-user-id>
                .handler(":user/connect", this::handleConnectionCreationAsync)
                .handler(context ->
                        //specify handlers for HTTP methods, (i.e. GET /users -> handleGetAllUsersAsync)
                        context.byMethod(spec ->
                                spec.post(this::handleAddNewUserAsync)));

    }


    void handleAddNewUserAsync(Context context) {
        Action<Fulfiller<String>> action = fulfiller -> {
            User user = helper.fromBody(context, User.class);
            store.saveAsync(user, helper.newJsonConsumer(fulfiller), fulfiller::error);
        };
        HandlerHelper.handleRequestWithPromise(context, action);
    }

    void handleConnectionCreationAsync(Context context) {
        Action<Fulfiller<String>> action = fulfiller -> {
            String userId = context.getPathTokens().get(USER_PATH_TOKEN);
            MultiValueMap<String, String> queryParams = context.getRequest().getQueryParams();
            if (!queryParams.containsKey(CONNECT_TO_QUERY_PARAM)) {
                throw new InvalidValueException("Query parameter 'to' is required.");
            }
            String connectTo = queryParams.get(CONNECT_TO_QUERY_PARAM);
            store.connectAsync(userId, connectTo, helper.newJsonConsumer(fulfiller), fulfiller::error);
        };
        HandlerHelper.handleRequestWithPromise(context, action);
    }

    void handleGetUserConnectionsListAsync(Context context) {
        Action<Fulfiller<String>> action = fulfiller -> {
            String userId = context.getPathTokens().get(USER_PATH_TOKEN);
            //other parameters like sort and count can be read from query parameters
            store.listConnections(userId, 100, helper.newJsonConsumer(fulfiller), fulfiller::error);
        };
        HandlerHelper.handleRequestWithPromise(context, action);
    }
}
