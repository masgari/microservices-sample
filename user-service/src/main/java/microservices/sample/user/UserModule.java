package microservices.sample.user;

import com.google.inject.AbstractModule;
import microservices.sample.user.ratpack.UserHandlerFactory;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserHandlerFactory.class);
    }
}
