package main.businesslogic.procedure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcedureManager {

    public ProcedureManager() {
        Procedure.loadAllProcedure();
    }

    public ObservableList<Procedure> getRecipes() {
        return FXCollections.unmodifiableObservableList(Procedure.getAllRecipes());
    }

    public ObservableList<Procedure> getPreparation() {
        return FXCollections.unmodifiableObservableList(Procedure.getAllPreparation());
    }
    public ObservableList<Procedure> getAllProcedure() {
        return FXCollections.unmodifiableObservableList(Procedure.getAllProcedure());
    }
}
