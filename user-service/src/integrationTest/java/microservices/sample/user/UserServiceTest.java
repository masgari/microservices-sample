package microservices.sample.user;

import microservices.sample.ServiceBuilder;
import microservices.sample.base.AvailablePortProvider;
import microservices.sample.persistence.ratpack.PersistenceServer;
import microservices.sample.user.ratpack.UserServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 * Full functional test
 *
 * @author mamad
 * @since 18/03/15.
 */
public class UserServiceTest {
    public static final int USER_SERVER_PORT = AvailablePortProvider.between(2000, 3000).nextPort();
    public static final int PERSISTENCE_SERVER_PORT = AvailablePortProvider.between(4100, 5000).nextPort();
    public static final String IP_ADDRESS = "localhost";
    private UserServer userServer;
    private PersistenceServer persistenceServer;
    private UserService userService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        userServer = new UserServer(USER_SERVER_PORT);
        userServer.start();
        persistenceServer = new PersistenceServer(PERSISTENCE_SERVER_PORT, IP_ADDRESS);
        persistenceServer.start();

        assumeTrue(userServer.isRunning());
        assumeTrue(persistenceServer.isRunning());

        userService = ServiceBuilder.create(UserService.class).build(IP_ADDRESS, USER_SERVER_PORT);
    }

    @After
    public void tearDown() throws Exception {
        persistenceServer.stop();
        userServer.stop();
    }

    @Test
    public void testUserService() throws Exception {
        User u1 = new User("James Bond", "");
        String id1 = userService.addUser(u1).getId();
        User u2 = new User("King Kong", "");
        String id2 = userService.addUser(u2).getId();
        userService.connect(id1, id2);

        List<User> users = userService.listDirectConnections(id1);
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertThat(users.size(), equalTo(1));
        assertThat(users.get(0).getName(), equalTo(u2.getName()));
    }
}
