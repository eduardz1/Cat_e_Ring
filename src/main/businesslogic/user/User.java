package main.businesslogic.user;

import javafx.collections.FXCollections;
import main.persistence.PersistenceManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class User {

    private static final Map<Integer, User> loadedUsers = FXCollections.observableHashMap();
    private final Set<Role> roles;
    private int id;
    private String username;
    public User() {
        id = 0;
        username = "";
        this.roles = new HashSet<>();
    }

    public static User loadUserById(int uid) {
        if (loadedUsers.containsKey(uid))
            return loadedUsers.get(uid);

        String userQuery = "SELECT * FROM Users WHERE id='" + uid + "';";
        return getUser(userQuery);
    }

    public static User loadUser(String username) {
        String userQuery = "SELECT * FROM Users WHERE username='" + username + "';";
        return getUser(userQuery);
    }

    private static User getUser(String userQuery) {
        User u = new User();
        PersistenceManager.executeQuery(userQuery, rs -> {
            u.id = rs.getInt("id");
            u.username = rs.getString("username");
        });
        if (u.id > 0) {
            loadedUsers.put(u.id, u);
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + u.id + ";";
            PersistenceManager.executeQuery(roleQuery, rs -> {
                String role = rs.getString("role_id");
                switch (role.charAt(0)) {
                    case 'c' -> u.roles.add(Role.CUOCO);
                    case 'h' -> u.roles.add(Role.CHEF);
                    case 'o' -> u.roles.add(Role.ORGANIZZATORE);
                    case 's' -> u.roles.add(Role.SERVIZIO);
                }
            });
        }
        return u;
    }

    public boolean isChef() {
        return roles.contains(Role.CHEF);
    }

    public boolean isCook() {
        return roles.contains(Role.CUOCO);
    }

    public String getUserName() {
        return username;
    }

    // STATIC METHODS FOR PERSISTENCE

    public int getId() {
        return this.id;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(username);
        if (roles.size() > 0) {
            result.append(": ");

            for (User.Role r : roles) {
                result.append(r.toString()).append(" ");
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum Role {
        SERVIZIO, CUOCO, CHEF, ORGANIZZATORE
    }
}
