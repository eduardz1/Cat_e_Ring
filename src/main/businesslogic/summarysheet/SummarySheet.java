package main.businesslogic.summarysheet;

import java.util.ArrayList;
import java.util.Map;

import javafx.collections.FXCollections;
import main.businesslogic.event.Event;
import main.businesslogic.menu.MenuItem;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.service.Service;

/**
 * SumarySheet
 */
public class SummarySheet {
    private static final Map<Integer, SummarySheet> loadedSheets = FXCollections.observableHashMap();
    private int id;
    private Service service;
    private final ArrayList<Assignment> assignments;

    protected SummarySheet(Service service) {
        this.service = service;
        this.assignments = new ArrayList<>();

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
        // TODO Auto-generated method stub  controlla se esiste un assegnamento con quella procedura
        return false;
    }

    public void assignmentCompleted(Assignment assignment) {
       assignment.setReady(); 
    }

    public int hasAssignment (Assignment assignment) {   // controlla se nel foglio esiste un dato assegnamento
        return this.assignments.indexOf(assignment);
    }

    public void removeProcedure(Procedure pro) {
        for (Assignment assignment : this.assignments) {
            if (assignment.getProcedure() == pro) {
                this.assignments.remove(assignment);
                break;
            }
        }
    }

    public ArrayList<Assignment> getAssignments() {
        return this.assignments;
    }

    public void moveAssignments(Assignment as, int position) {
        this.assignments.remove(as);
        this.assignments.add(position, as);
    }
}