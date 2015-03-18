package microservices.sample.persistence;

import microservices.sample.IdResponse;
import microservices.sample.user.User;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Generic persistence service.
 * The input, output, full URL and parameters must be consistent with handlers (i.e. PersistenceHandlerFactory)
 *
 * @see microservices.sample.persistence.ratpack.PersistenceHandlerFactory
 * @author mamad
 * @since 15/03/15.
 */
public interface PersistenceService {
    /**
     * Save the entity.
     * @param entity The entity
     * @return id of the entity
     */
    @POST("/v1/entities")
    IdResponse save(@Body User entity);

    @GET("/v1/entities/{id}")
    User findById(@Path("id") String id);
}
