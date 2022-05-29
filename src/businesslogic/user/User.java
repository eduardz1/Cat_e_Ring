package businesslogic.user;

import javafx.collections.FXCollections;
import persistence.PersistenceManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {

    private static final Map<Integer, User> loadedUsers = FXCollections.observableHashMap();

    public enum Role {
        SERVIZIO, CUOCO, CHEF, ORGANIZZATORE
    }

    private int id;
    private String username;
    private final Set<Role> roles;

    public User() {
        id = 0;
        username = "";
        this.roles = new HashSet<>();
    }

    public boolean isChef() {
        return roles.contains(Role.CHEF);
    }

    public String getUserName() {
        return username;
    }

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

    // STATIC METHODS FOR PERSISTENCE

    public static User loadUserById(int uid) {
        if (loadedUsers.containsKey(uid))
            return loadedUsers.get(uid);

        String userQuery = "SELECT * FROM Users WHERE id='" + uid + "'";
        return getUser(userQuery);
    }

    public static User loadUser(String username) {
        String userQuery = "SELECT * FROM Users WHERE username='" + username + "'";
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
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + u.id;
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
}
