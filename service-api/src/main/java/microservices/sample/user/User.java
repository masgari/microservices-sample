package microservices.sample.user;

import java.util.Objects;

/**
 * @author mamad
 * @since 15/03/15.
 */
public class User {
    private final String id;
    private final String name;

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
}

