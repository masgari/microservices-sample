package microservices.sample.user;

import microservices.sample.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class User implements Entity {
    private final String id;
    private final String name;

    //using list for simplicity, to keep the order
    private final List<String> connectionIds = new ArrayList<>();

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return Objects.equals(this.name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public User addConnection(String secondUserId) {
        if (secondUserId != null && !connectionIds.contains(secondUserId)) {
            connectionIds.add(secondUserId);
        }
        return this;
    }

    public List<String> getConnectionIds() {
        return connectionIds;
    }
}

