package microservices.sample.base.ratpack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;

/**
 * A Guice module provides common bindings for micro-services.
 *
 * @author mamad
 * @since 17/03/15.
 */
public class BaseModule extends AbstractModule {
    @Override
    protected void configure() {
        //create customised Gson if needed
        bind(Gson.class).toInstance(new GsonBuilder().create());

        //bind more common classes here
    }
}
