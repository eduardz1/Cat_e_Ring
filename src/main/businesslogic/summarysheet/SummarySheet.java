package main.businesslogic.summarysheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import main.businesslogic.event.Event;
import main.businesslogic.menu.MenuItem;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.service.Service;
import org.apache.commons.collections4.map.ListOrderedMap;

/**
 * SumarySheet
 */
public class SummarySheet {
    private static final Map<Integer, SummarySheet> loadedSheets = FXCollections.observableHashMap();
    private int id;
    private final Service service;
    private final ListOrderedMap<Assignment, Assignment> assignments;
    protected SummarySheet(Service service) {
        this.service = service;
        this.assignments = new ListOrderedMap<>();

        for (MenuItem item : service.getMenu().getAllItems()) {
            Assignment assignment = new Assignment(item.getItemRecipe());
            this.assignments.put(assignment, null);
        }
    }

    public Assignment addAssignment(Procedure pro) {
        Assignment as = new Assignment(pro);
        this.assignments.put(as, null);
        return as;
    }

    public boolean isAssigned(Procedure pro) {
        return this.assignments.keySet().stream().anyMatch(as -> as.getProcedure() == pro);
    }

    public void assignmentCompleted(Assignment assignment) {
       assignment.setReady(); 
    }

    public boolean hasAssignment(Assignment assignment) {   // controlla se nel foglio esiste un dato assegnamento
        return this.assignments.containsKey(assignment);
    }

    public void removeProcedure(Procedure pro) {
        for (Assignment assignment : this.assignments.keySet()) {
            if (assignment.getProcedure() == pro) {
                this.assignments.remove(assignment);
            }
        }
    }

    public ArrayList<Assignment> getAssignments() {
        return new ArrayList<>(this.assignments.keySet());
    }

    public void moveAssignments(Assignment as, int position) {
        Assignment continuation = this.assignments.remove(as);
        this.assignments.put(position, as, continuation);
    }

    public void removeAssignment(Assignment as) {
        this.assignments.forEach((k, v) -> {
            if (v == as) {
                this.assignments.put(k, assignments.get(as));
            }
        });
        this.assignments.remove(as);
    }
}