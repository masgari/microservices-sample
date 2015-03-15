package microservices.sample.user;

import java.util.Objects;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class Connection {
    private final String id;
    private final String user1;
    private final String user2;

    public Connection(String id, String user1, String user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getId() {
        return id;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Connection)) {
            return false;
        }

        Connection that = (Connection) o;
        return Objects.equals(this.user1, that.user1) && Objects.equals(this.user2, that.user2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }
}
