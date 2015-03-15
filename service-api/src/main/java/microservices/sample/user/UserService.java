package microservices.sample.user;

import java.util.List;

/**
 * @author mamad
 * @since 15/03/15.
 */
public interface UserService {
    String addUser(User user);

    /**
     * Connect user1 to user2
     * @return connection id
     */
    String connect(String user1, String user2);

    List<User> listDirectConnection(String name, int startIndex, int size);
}
