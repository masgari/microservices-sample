package microservices.sample.user.ratpack;

import com.google.inject.Singleton;
import microservices.sample.base.ratpack.StatusHelper;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Context;

/**
 * @author mamad
 * @since 15/03/15.
 */
@Singleton
public class UserHandlerFactory implements Action<Chain> {
    @Override
    public void execute(Chain chain) throws Exception {
        //handle all requests to /users
        chain.prefix("users", action -> action.handler(ctx ->
                //specify handlers for HTTP methods, (i.e. GET /users -> handleGetAllUsersAsync)
                ctx.byMethod(spec -> spec.get(this::handleGetAllUsersAsync)
                        .post(this::handleAddNewUserAsync))))
                //default handler, show REST endpoints
                .handler(ctx -> ctx.render("Users Service - list users: 'GET /v1/users' "));
    }

    void handleAddNewUserAsync(Context ctx) {
        StatusHelper.sendNotImplemented(ctx, "add-user");
    }

    void handleGetAllUsersAsync(Context ctx) {
        StatusHelper.sendNotImplemented(ctx, "list-users");
    }
}
