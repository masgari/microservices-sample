package microservices.sample.user;

import microservices.sample.IdResponse;
import retrofit.http.*;

import java.util.List;

/**
 * @author mamad
 * @since 15/03/15.
 */
public interface UserService {
    @POST("/v1/users")
    IdResponse addUser(@Body User user);

    /**
     * Connect user1 to user2
     * @param id id of user1
     * @param to id of user2
     * @return connection id
     */
    @POST("/v1/users/{id}/connect")
    IdResponse connect(@Path("id") String id, @Query("to") String to);

    @GET("/v1/users/{id}/connections")
    List<User> listDirectConnection(@Path("id") String id);
}
