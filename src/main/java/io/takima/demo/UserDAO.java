package io.takima.demo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDAO {
    private List<User> users = new ArrayList<>();

    public UserDAO() {
        users.add(new User(1L, "Loïc", "Ortola", 31));
        users.add(new User(2L, "Thomas", "Blandin", 22));
        users.add(new User(3L, "Alexandre", "Moray", 23));
    }

    public List<User> getAll() {
        return Collections.unmodifiableList(users);
    }

    public void create(User u) {
        users.add(u);
    }
}
