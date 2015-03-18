package microservices.sample.persistence.ratpack;

import com.google.inject.Singleton;
import ratpack.func.Action;
import ratpack.handling.Chain;


/**
 * This class is main REST requests handler.
 * To follow REST api design standards, define root collections with .prefix() here.
 * Each collection has its own handler.
 * <p>
 * Currently, we only have one collection: entities
 *
 * @author mamad
 * @since 17/03/15.
 */
@Singleton
public class PersistenceHandlerFactory implements Action<Chain> {

    @Override
    public void execute(Chain chain) throws Exception {
        chain.prefix(EntitiesCollectionHandler.COLLECTION_NAME, chain.getRegistry().get(EntitiesCollectionHandler.class))
                //default handler, show REST endpoints
                .handler(ctx -> ctx.render("Persistence Service:\nSave: POST /v1/entities\nGet by id: GET /v1/entities/:id"));
    }
}
