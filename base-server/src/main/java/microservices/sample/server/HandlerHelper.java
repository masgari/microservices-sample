package microservices.sample.server;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper class for reading and writing json objects in ratpack handlers.
 *
 * @author mamad
 * @since 15/03/15.
 */
@Singleton
public class HandlerHelper {
    private final Gson gson;

    @Inject
    public HandlerHelper(Gson gson) {
        this.gson = gson;
    }
}
