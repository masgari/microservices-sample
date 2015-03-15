package microservices.sample.user;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

/**
 * @author mamad
 * @since 15/03/15.
 */
public interface UserService {
    @POST("/v1/users")
    String addUser(@Body User user);

    /**
     * Connect user1 to user2
     * @param id id of user1
     * @param to id of user2
     * @return connection id
     */
    @POST("/v1/users/{id}/connect")
    String connect(@Path("id") String id, @Query("to") String to);

    List<User> listDirectConnection(String name, int startIndex, int size);
}
