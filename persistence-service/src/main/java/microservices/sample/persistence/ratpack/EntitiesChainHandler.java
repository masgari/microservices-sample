package microservices.sample.persistence.ratpack;

import com.google.inject.Inject;
import microservices.sample.base.ratpack.HandlerHelper;
import microservices.sample.base.ratpack.StatusHelper;
import microservices.sample.persistence.Store;
import ratpack.exec.Fulfiller;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Context;

/**
 * Entity collection handler, mapped to '/v1/entities'
 *
 * @author mamad
 * @since 17/03/15.
 */
public class EntitiesChainHandler implements Action<Chain> {
    //rest collection name
    public static final String COLLECTION_NAME = "entities";

    private final HandlerHelper helper;
    private final Store store;

    @Inject
    public EntitiesChainHandler(HandlerHelper helper, Store store) {
        this.helper = helper;
        this.store = store;
    }


    @Override
    public void execute(Chain chain) throws Exception {
        //read id parameter from path
        chain
                //mapped to /v1/entities/:id
                .handler(":id", ctx -> ctx.byMethod(spec -> spec.get(this::handleFindByIdAsync)))
                        //mapped to /v1/entities
                .handler(ctx -> ctx.byMethod(spec -> spec.get(this::handleGetListAsync).post(this::handleSaveAsync)));
    }


    private void handleSaveAsync(Context context) {
        Action<Fulfiller<String>> action = fulfiller -> {
            Object entity = helper.fromBody(context, Object.class);
            store.saveAsync(entity, helper.newJsonConsumer(fulfiller), fulfiller::error);
        };
        handleRequestWithPromise(context, action);
    }

    private void handleRequestWithPromise(Context context, Action<Fulfiller<String>> action) {
        context.promise(action).onError(HandlerHelper.newErrorAction(context)).then(HandlerHelper.newJsonAction(context));
    }

    private void handleGetListAsync(Context context) {
        StatusHelper.sendNotImplemented(context, "get-list");
    }

    private void handleFindByIdAsync(Context context) {
        Action<Fulfiller<String>> action = fulfiller -> {
            String id = context.getPathTokens().get("id");
            store.findById(id, helper.newJsonConsumer(fulfiller), fulfiller::error);
        };
        handleRequestWithPromise(context, action);
    }

}
