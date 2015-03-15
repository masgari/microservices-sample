package microservices.sample.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Gson.class).toInstance(new GsonBuilder().create());
    }
}
