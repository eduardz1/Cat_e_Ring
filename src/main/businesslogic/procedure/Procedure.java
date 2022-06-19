package main.businesslogic.procedure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.persistence.PersistenceManager;

import java.util.Objects;


public class Procedure {
    private static final ObservableList<Procedure> all = FXCollections.observableArrayList();
    protected int id;
    protected String name;
    protected boolean procedureType;   // TRUE = ricetta FALSE = preparazione
    public Procedure(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procedure procedure = (Procedure) o;
        return id == procedure.id && procedureType == procedure.procedureType && name.equals(procedure.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, procedureType);
    }

    public static ObservableList<Procedure> loadAllProcedure() {
        String query = "SELECT * FROM procedure WHERE 1;";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                        int id = rs.getInt("id");
                        Procedure rec = new Procedure(rs.getString("name"));
                        rec.id = id;
                        if(rs.getString("procedureType") == "ricetta") rec.procedureType=true;
                        else rec.procedureType=false;
                       if(!all.contains(rec)) all.add(rec);
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
        String query = "SELECT * FROM procedure WHERE id = " + id + ";";
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
