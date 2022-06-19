package main.businesslogic.procedure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcedureManager {
    private final ObservableList<Procedure> procedureBoard;

    public ProcedureManager() {
        this.procedureBoard = FXCollections.observableArrayList();
        this.procedureBoard.addAll(Procedure.loadAllProcedures());
    }

    public ObservableList<Procedure> getRecipes() {
        return FXCollections.observableArrayList(procedureBoard.filtered(Procedure::isRecipe));
    }

    public ObservableList<Procedure> getPreparations() {
        return FXCollections.observableArrayList(procedureBoard.filtered(Procedure::isPreparation));
    }

    public ObservableList<Procedure> getAllProcedures() {
        return procedureBoard;
    }
}
