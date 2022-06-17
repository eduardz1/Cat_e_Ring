package main.persistence;

import java.io.Console;
import java.sql.*;
import java.util.Scanner;

public class PersistenceManager {
    private static final String url = "jdbc:mysql://localhost:3306/catering?serverTimezone=UTC";
    private static String username;
    private static String password;

    private static int lastId;

    public static String escapeString(String input) {
        input = input.replace("\\", "\\\\");
        input = input.replace("'", "\\'");
        input = input.replace("\"", "\\\"");
        input = input.replace("\n", "\\n");
        input = input.replace("\t", "\\t");
        return input;
    }

    private static void logIn() {
        if (username != null && password != null) return;

        System.out.println("Inserire username e password per accedere al database");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username: ");
        username = scanner.nextLine();

        Console console = System.console();
        if (console != null) { // if running on a terminal instead of an IDE
            password = new String(console.readPassword("Password: "));
        } else {
            System.out.println("Password: ");
            password = scanner.nextLine();
        }

        scanner.close();
    }

    public static void testSQLConnection() {
        logIn();
        try (Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                System.out.println(name + " ha id = " + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void executeQuery(String query, ResultHandler handler) {
        logIn();
        try (Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                handler.handle(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static int[] executeBatchUpdate(
            String parametrizedQuery, int itemNumber, BatchUpdateHandler handler) {
        int[] result = new int[0];
        logIn();
        try (Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement ps =
                        conn.prepareStatement(parametrizedQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < itemNumber; i++) {
                handler.handleBatchItem(ps, i);
                ps.addBatch();
            }
            result = ps.executeBatch();
            ResultSet keys = ps.getGeneratedKeys();
            int count = 0;
            while (keys.next()) {
                handler.handleGeneratedIds(keys, count);
                count++;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static int executeUpdate(String update) {
        int result = 0;
        logIn();
        try (Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement ps =
                        conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {
            result = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            } else {
                lastId = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static int getLastId() {
        return lastId;
    }
}
