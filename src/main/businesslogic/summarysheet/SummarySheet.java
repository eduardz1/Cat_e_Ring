package main.businesslogic.summarysheet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.menu.MenuItem;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.service.Service;
import main.businesslogic.shift.Shift;
import main.businesslogic.user.User;
import main.persistence.BatchUpdateHandler;
import main.persistence.PersistenceManager;

/**
 * SummarySheet
 */
public class SummarySheet {
    private static final Map<Integer, SummarySheet> loadedSheets = FXCollections.observableHashMap();
    private int id;
    private final Service service;
    private final ObservableList<Assignment> assignments;

    private final User owner; // FIXME add this in the DSD

    protected SummarySheet(Service service) {
        this.service = service;
        this.assignments = FXCollections.observableArrayList();
        this.owner = service.linkedEvent().getChefAssigned();

        for (MenuItem item : service.referencedMenu().getAllItems()) {
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
        this.assignments.removeIf(assignment -> assignment.getProcedure() == pro);
    }

    public ObservableList<Assignment> getAssignments() {
        return FXCollections.unmodifiableObservableList(this.assignments);
    }

    public void moveAssignments(Assignment as, int position) {
        this.assignments.remove(as);
        this.assignments.add(position, as);
    }

    public void deleteAssignment(Assignment as) {
        for(Assignment continuation : this.assignments) {
            if(continuation.getContinuation() == as) {
                continuation.setContinuation(as.getContinuation());
            }
        }
        this.assignments.remove(as);
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SummarySheet di ID: " + id +
                ", si riferisce al servizio: " + service +
                ", ed ha i seguenti assegnamenti: " + assignments;
    }

    private void updateAssignments(ObservableList<Assignment> newAssignments) {
        // TODO
    }

    // STATIC METHODS FOR PERSISTENCE

    public static void saveNewSummarySheet(SummarySheet ss) {
        String summarySheetInsert = "INSERT INTO catering.SummarySheets (owner_id) VALUES (?);";
        int res = PersistenceManager.executeUpdate(summarySheetInsert); // FIXME I have no idea how this works

        if(res <= 0) return; // menu non inserito

        if(ss.assignments.size() > 0) {
            Assignment.saveAllNewAssignments(ss.id, ss.assignments);
        }

        loadedSheets.put(ss.id, ss);
    }

    public static void deleteSummarySheet(SummarySheet ss) {
        // delete assignments
        String delAss = "DELETE FROM SummarySheetAssignments WHERE summarysheet_id = " + ss.id;
        PersistenceManager.executeUpdate(delAss);

        String del = "DELETE FROM SummarySheets WHERE id = " + ss.id;
        PersistenceManager.executeUpdate(del);
        loadedSheets.remove(ss); // FIXME
    }

    public static void saveAssignmentsOrder(SummarySheet ss) {
        String upd = "UPDATE SummarySheetAssignments SET position = ? WHERE id = ?";
        PersistenceManager.executeBatchUpdate(upd, ss.assignments.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, batchCount);
                ps.setInt(2, ss.assignments.get(batchCount).getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // no generated ids to handle
            }
        });
    }

    public Assignment defineAssignment(Assignment assignment,
                                       Optional<Integer> quantity,
                                       Optional<Shift> shift,
                                       Optional<User> cook,
                                       Optional<Duration> estimatedTime,
                                       Optional<Assignment> continuation) throws UseCaseLogicException {
        quantity.ifPresent(assignment::setQuantity);
        continuation.ifPresent(assignment::setContinuation);

        // Can throw exception if the time can´t be assigned
        if (cook.isPresent()) assignment.setCook(cook.get());
        if (shift.isPresent()) assignment.setShift(shift.get());
        if (estimatedTime.isPresent()) assignment.setEstimatedTime(estimatedTime.get());

        return assignment;
    }
}