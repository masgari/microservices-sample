package microservices.sample.user.ratpack;

import com.google.inject.Singleton;
import ratpack.func.Action;
import ratpack.handling.Chain;

/**
 * @author mamad
 * @since 15/03/15.
 */
@Singleton
public class UserHandlerFactory implements Action<Chain> {
    @Override
    public void execute(Chain chain) throws Exception {
        //handle all requests to /users
        chain.prefix(UsersCollectionHandler.COLLECTION_NAME, chain.getRegistry().get(UsersCollectionHandler.class))
                //default handler, show REST endpoints
                .handler(ctx -> ctx.render("Users Service - list users: 'GET /v1/users' "));
    }
}
