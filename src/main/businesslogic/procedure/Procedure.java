package main.businesslogic.procedure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.persistence.PersistenceManager;

public class Procedure {
    protected int id;
    protected String name;
    protected boolean isRecipe; // TRUE = ricetta FALSE = preparazione

    public Procedure(String name) {
        this.name = name;
    }

    public static ObservableList<Procedure> loadAllProcedures() {
        ObservableList<Procedure> res = FXCollections.observableArrayList();
        String query = "SELECT * FROM Procedures;";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    int id = rs.getInt("id");
                    Procedure rec = new Procedure(rs.getString("name"));
                    rec.id = id;
                    rec.isRecipe = rs.getBoolean("isRecipe");
                    res.add(rec);
                });
        return res;
    }

    public static Procedure loadProcedureById(int id) {
        ObservableList<Procedure> res = FXCollections.observableArrayList();
        String query = "SELECT * FROM Procedures WHERE id = " + id + ";";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    Procedure rec = new Procedure(rs.getString("name"));
                    rec.id = id;
                    rec.isRecipe = rs.getBoolean("isRecipe");
                    res.add(rec);
                });
        return res.get(0);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }

    public boolean isRecipe() {
        return this.isRecipe;
    }

    public boolean isPreparation() {
        return !this.isRecipe;
    }
}
