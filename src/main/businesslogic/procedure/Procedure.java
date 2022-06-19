package main.businesslogic.procedure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.persistence.PersistenceManager;


public class Procedure {
    private static final ObservableList<Procedure> all = FXCollections.observableArrayList();
    protected int id;
    protected String name;
    protected boolean procedureType;   // TRUE = ricetta FALSE = preparazione
    public Procedure(String name) {
        this.name = name;
    }

    public static ObservableList<Procedure> loadAllProcedure() {
        String query = "SELECT * FROM Procedure;";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                        int id = rs.getInt("id");
                        Procedure rec = new Procedure(rs.getString("name"));
                        rec.id = id;
                        if(rs.getString("procedureType") == "ricetta") rec.procedureType=true;
                        else rec.procedureType=false;
                        all.add(rec);
                });
        return all;
    }

    public static ObservableList<Procedure> getAllRecipes() {
        ObservableList<Procedure> result = FXCollections.observableArrayList();
        for (Procedure procedure : all) {
            if (procedure.procedureType == true) result.add(procedure);
        }
        return result;
    }

    public static ObservableList<Procedure> getAllPreparation() {
        ObservableList<Procedure> result = FXCollections.observableArrayList();
        for (Procedure procedure : all) {
            if (procedure.procedureType == false) result.add(procedure);
        }
        return result;
    }

    public static ObservableList<Procedure> getAllProcedure() {
        ObservableList<Procedure> result = FXCollections.observableArrayList();
        for (Procedure procedure : all) {
             result.add(procedure);
        }
        return result;
    }

    public static Procedure loadProcedureById(int id) {
        for (Procedure procedure : all) {
            if (procedure.getId() == id) return procedure;
        }
        String query = "SELECT * FROM Procedure WHERE id = " + id + ";";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    Procedure rec = new Procedure(rs.getString("name"));
                    rec.id = id;
                    if(rs.getString("procedureType") == "ricetta") rec.procedureType=true;
                    else rec.procedureType=false;
                    all.add(rec);
                });
       return all.get(all.size()-1);
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
}
