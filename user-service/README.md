# User Service

```java
public interface UserService {
    @POST("/v1/users")
    IdResponse addUser(@Body User user);

    //example: POST /v1/users/adam/connect?to=eve
    @POST("/v1/users/{name}/connect")
    GenericResponse connect(@Path("name") String name, @Query("to") String to);

    @GET("/v1/users/{name}/connections")
    List<User> listDirectConnections(@Path("name") String name);
}
```