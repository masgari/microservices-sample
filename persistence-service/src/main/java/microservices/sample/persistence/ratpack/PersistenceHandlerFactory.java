package microservices.sample.persistence.ratpack;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import microservices.sample.base.ratpack.HandlerHelper;
import microservices.sample.base.ratpack.StatusHelper;
import microservices.sample.persistence.Store;
import ratpack.exec.Fulfiller;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Context;


/**
 * This class is main REST requests handler.
 *
 * @author mamad
 * @since 17/03/15.
 */
@Singleton
public class PersistenceHandlerFactory implements Action<Chain> {
    private final HandlerHelper helper;
    private final Store store;

    @Inject
    public PersistenceHandlerFactory(HandlerHelper helper, Store store) {
        this.helper = helper;
        this.store = store;
    }

    @Override
    public void execute(Chain chain) throws Exception {
        chain.prefix("persist", action -> action
                //read id parameter from path
                .handler(":id", this::handleFindByIdAsync))
                .handler(ctx -> ctx.byMethod(spec -> spec.post(this::handleSaveAsync)))
                        //default handler, show REST endpoints
                .handler(ctx ->
                        ctx.render("Persistence Service:\nSave: POST /v1/persist\nFindById: GET /v1/persist/:id"));
    }

    private void handleSaveAsync(Context context) {
        context.promise(new Action<Fulfiller<String>>() {
            @Override
            public void execute(Fulfiller<String> fulfiller) throws Exception {
                Object entity = helper.fromBody(context, Object.class);
                store.saveAsync(entity, helper.newJsonConsumer(fulfiller), fulfiller::error);
            }
        }).onError(HandlerHelper.newErrorAction(context)).then(HandlerHelper.newJsonAction(context));

        StatusHelper.sendNotImplemented(context, "save");
    }

    private void handleFindByIdAsync(Context context) {
        StatusHelper.sendNotImplemented(context, "find-by-id");
    }
}
