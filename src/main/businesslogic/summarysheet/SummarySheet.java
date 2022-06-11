package main.businesslogic.summarysheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.event.Event;
import main.businesslogic.menu.MenuItem;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.service.Service;

/**
 * SummarySheet
 */
public class SummarySheet {
    private static final Map<Integer, SummarySheet> loadedSheets = FXCollections.observableHashMap();
    private int id;
    private final Service service;
    private final ObservableList<Assignment> assignments;
    protected SummarySheet(Service service) {
        this.service = service;
        this.assignments = FXCollections.observableArrayList();

        for (MenuItem item : service.getMenu().getAllItems()) {
            Assignment assignment = new Assignment(item.getItemRecipe());
            this.assignments.add(assignment);
        }
    }

    public Assignment addAssignment(Procedure pro) {
        Assignment as = new Assignment(pro);
        this.assignments.add(as);
        return as;
    }

    public boolean isAssigned(Procedure pro) {
        return this.assignments.stream().anyMatch(as -> as.getProcedure() == pro);
    }

    public void assignmentCompleted(Assignment assignment) {
       assignment.setReady(); 
    }

    public boolean hasAssignment(Assignment assignment) {   // controlla se nel foglio esiste un dato assegnamento
        return this.assignments.contains(assignment);
    }

    public void removeProcedure(Procedure pro) {
        for (Assignment assignment : this.assignments) {
            if (assignment.getProcedure() == pro) {
                this.assignments.remove(assignment);
            }
        }
    }

    public ArrayList<Assignment> getAssignments() {
        return new ArrayList<>(this.assignments);
    }

    public void moveAssignments(Assignment as, int position) {
        this.assignments.remove(as);
        this.assignments.add(position, as);
    }

    public void removeAssignment(Assignment as) {
        for(Assignment continuation : this.assignments) {
            if(continuation.getContinuation() == as) {
                continuation.setContinuation(as.getContinuation());
            }
        }
        this.assignments.remove(as);
    }
}