package main.businesslogic.summarysheet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.menu.MenuItem;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.shift.Shift;
import main.businesslogic.user.User;
import main.persistence.BatchUpdateHandler;
import main.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

/** SummarySheet */
public class SummarySheet {
    private static final Map<Integer, SummarySheet> loadedSheets =
            FXCollections.observableHashMap();
    private final ServiceInfo service;
    private final ObservableList<Assignment> assignments;
    private final User owner;
    private int id;

    protected SummarySheet(ServiceInfo service) {
        this.id = 0;
        this.service = service;
        this.assignments = FXCollections.observableArrayList();
        this.owner = service.getEventInfo().getOrganizer();

        for (MenuItem item : service.getMenu().getAllItems()) {
            Assignment assignment = new Assignment(item.getItemProcedure());
            this.assignments.add(assignment);
        }
    }

    public static void saveNewSummarySheet(SummarySheet ss) {
        String summarySheetInsert =
                "INSERT INTO catering.SummarySheets (id, id_service) VALUES (?, ?);";
        PersistenceManager.executeBatchUpdate(
                summarySheetInsert,
                1,
                new BatchUpdateHandler() {
                    @Override
                    public void handleBatchItem(PreparedStatement ps, int batchCount)
                            throws SQLException {
                        ps.setInt(1, ss.id);
                        ps.setInt(2, ss.service.getId());
                    }

                    @Override
                    public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                        if (count == 0) {
                            ss.id = rs.getInt(1);
                        }
                    }
                });
        if (ss.assignments.size() > 0) {
            Assignment.saveAllNewAssignments(ss.id, ss.assignments);
        }
        loadedSheets.put(ss.id, ss);
    }

    public static ObservableList<SummarySheet> loadAllSummarySheets() {
        String query = "SELECT * FROM SummarySheets WHERE " + true;
        Map<Integer, SummarySheet> newSheets = FXCollections.observableHashMap();

        PersistenceManager.executeQuery(
                query,
                rs -> {
                    int id = rs.getInt("id");
                    if (!loadedSheets.containsKey(id)) {
                        ServiceInfo service = ServiceInfo.loadServiceInfo(rs.getInt("id_service"));
                        SummarySheet ss = new SummarySheet(service);
                        ss.id = id;
                        ss.assignments.clear();
                        ss.assignments.addAll(Assignment.loadAllAssignmentsForSummarySheet(id));
                        newSheets.put(ss.getId(), ss);
                    }
                });

        loadedSheets.putAll(newSheets);
        return FXCollections.observableArrayList(loadedSheets.values());
    }

    public static void saveAssignmentsOrder(SummarySheet ss) {
        String upd = "UPDATE Assignments SET position = ? WHERE id = ?;";
        PersistenceManager.executeBatchUpdate(
                upd,
                ss.assignments.size(),
                new BatchUpdateHandler() {
                    @Override
                    public void handleBatchItem(PreparedStatement ps, int batchCount)
                            throws SQLException {
                        ps.setInt(1, batchCount);
                        ps.setInt(2, ss.assignments.get(batchCount).getId());
                    }

                    @Override
                    public void handleGeneratedIds(ResultSet rs, int count) {
                        // no generated ids to handle
                    }
                });
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

    public boolean hasAssignment(
            Assignment assignment) { // controlla se nel foglio esiste un dato assegnamento
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
        for (Assignment continuation : this.assignments) {

            if (as.isDefined()) {
                as.getSelShift().increaseAvailableTime(as.getSelCook(), as.getEstimatedTime());
            }
            if (continuation.getContinuation() == as) {
                continuation.setContinuation(as.getContinuation());
            }
        }
        this.assignments.remove(as);
    }

    public User getOwner() {
        return this.owner;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FOGLIO RIEPILOGATIVO (id=")
                .append(this.id)
                .append(")\n")
                .append("\t--> riferito al SERVIZIO: ")
                .append(this.service.getName())
                .append("\n")
                .append("\t--> contiene gli ASSEGNAMENTI:\n");
        this.assignments.forEach(
                assignment -> builder.append("\t\t").append(assignment.toString()).append("\n"));
        return builder.toString();
    }

    public Assignment defineAssignment(
            Assignment assignment,
            Optional<Integer> quantity,
            Optional<Shift> shift,
            Optional<User> cook,
            Optional<Duration> estimatedTime,
            Optional<Assignment> continuation)
            throws UseCaseLogicException {
        quantity.ifPresent(assignment::setQuantity);
        continuation.ifPresent(assignment::setContinuation);

        // Can throw exception if the time can´t be assigned
        if (cook.isPresent()) assignment.setCook(cook.get());
        if (shift.isPresent()) assignment.setShift(shift.get());
        if (estimatedTime.isPresent()) assignment.setEstimatedTime(estimatedTime.get());

        return assignment;
    }

    public ServiceInfo getService() {
        return service;
    }
}
