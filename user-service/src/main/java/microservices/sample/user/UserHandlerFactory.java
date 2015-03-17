package microservices.sample.user;

import microservices.sample.base.StatusHelper;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Context;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class UserHandlerFactory implements Action<Chain> {
    @Override
    public void execute(Chain chain) throws Exception {
        chain.prefix("users", action -> action.handler(ctx ->
                ctx.byMethod(spec -> spec.get(this::handleGetAllUsersAsync)
                        .post(this::handleAddNewUserAsync))))
                .handler(ctx -> ctx.render("Users Service - list users: 'get /v1/users' "));
    }

    void handleAddNewUserAsync(Context ctx) {
        StatusHelper.sendNotImplemented(ctx, "add-user");
    }

    void handleGetAllUsersAsync(Context ctx) {
        StatusHelper.sendNotImplemented(ctx, "list-users");
    }
}
