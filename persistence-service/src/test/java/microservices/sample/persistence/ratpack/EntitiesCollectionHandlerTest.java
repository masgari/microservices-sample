package microservices.sample.persistence.ratpack;

import com.google.common.collect.ImmutableMap;
import com.google.gson.GsonBuilder;
import microservices.sample.base.ratpack.HandlerHelper;
import microservices.sample.persistence.EntityStore;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ratpack.test.handling.HandlingResult;
import ratpack.test.handling.RequestFixture;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

/**
 * @author mamad
 * @since 17/03/15.
 */
public class EntitiesCollectionHandlerTest {

    HandlerHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new HandlerHelper(new GsonBuilder().create());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindById() throws Exception {

        EntityStore store = mock(EntityStore.class);
        //configure mocked store, otherwise async request with not handled
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Consumer<Object> onSuccessCallback = (Consumer<Object>) args[1];
            onSuccessCallback.accept(ImmutableMap.of("a", "b"));
            return null;
        }).when(store).findById(eq("id1"), any(Consumer.class), any(Consumer.class));

        //get the item with id1
        HandlingResult result = RequestFixture.handle(new EntitiesCollectionHandler(helper, store), fixture ->
                        fixture.body("{\"a\":\"b\"}", "application/json").uri("id1")
        );

        Assert.assertThat(result.getBodyText(), Matchers.equalTo("{\"a\":\"b\"}"));
    }
}
