package main.businesslogic.summarysheet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.shift.Shift;
import main.businesslogic.user.User;
import main.persistence.BatchUpdateHandler;
import main.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Assignment {
    private static final Map<Integer, Assignment> loadedAssignments =
            FXCollections.observableHashMap();

    private boolean completed = false;
    private Integer quantity = 0;
    private Duration estimatedTime = Duration.ZERO;
    private Assignment continuation = null;
    private Shift selShift = null;
    private User selCook = null;
    private Procedure itemProcedure;

    private int id = 0;

    public Assignment(Procedure procedure) {
        this.itemProcedure = procedure;
    }

    public static void saveAllNewAssignments(int summarysheet_id, List<Assignment> assignments) {
        String query =
                "INSERT INTO catering.Assignments (id_summary_sheet, completed, quantity, estimatedTime, id_continuation, id_shift, id_cook, id_procedure, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PersistenceManager.executeBatchUpdate(
                query,
                assignments.size(),
                new BatchUpdateHandler() {
                    @Override
                    public void handleBatchItem(PreparedStatement ps, int batchCount)
                            throws SQLException {
                        ps.setInt(1, summarysheet_id);
                        ps.setBoolean(2, assignments.get(batchCount).isCompleted());
                        ps.setInt(3, assignments.get(batchCount).getQuantity());
                        ps.setLong(
                                4,
                                (int) assignments.get(batchCount).getEstimatedTime().toMinutes());
                        ps.setInt(5, 0);
                        ps.setInt(6, 0);
                        ps.setInt(7, 0);
                        ps.setInt(8, assignments.get(batchCount).getProcedure().getId());
                        ps.setInt(9, batchCount);
                    }

                    @Override
                    public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                        assignments.get(count).id = rs.getInt(1);
                    }
                });
        loadedAssignments.putAll(
                assignments.stream().collect(Collectors.toMap(Assignment::getId, a -> a)));
    }

    public static ObservableList<Assignment> loadAllAssignmentsForSummarySheet(int id2) {
        ObservableMap<Assignment, Integer> assignments = FXCollections.observableHashMap();
        String query = "SELECT * FROM catering.Assignments WHERE id_summary_sheet = " + id2 + ";";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    while (rs.next()) {
                        Assignment assignment =
                                new Assignment(
                                        Procedure.loadProcedureById(rs.getInt("id_procedure")));
                        assignment.id = rs.getInt("id");
                        assignment.completed = rs.getBoolean("completed");
                        assignment.quantity = rs.getInt("quantity");
                        assignment.estimatedTime = Duration.ofMinutes(rs.getLong("estimatedTime"));
                        assignment.continuation =
                                rs.getInt("id_continuation") == 0
                                        ? null
                                        : Assignment.loadAssignmentById(
                                                rs.getInt("id_continuation"));
                        assignment.selShift =
                                rs.getInt("id_shift") == 0
                                        ? null
                                        : Shift.loadShiftById(rs.getInt("id_shift"));
                        assignment.selCook =
                                rs.getInt("id_cook") == 0
                                        ? null
                                        : User.loadUserById(rs.getInt("id_cook"));
                        assignments.put(assignment, rs.getInt("position"));
                    }
                });

        ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();
        assignmentList.addAll(
                assignments.keySet().stream()
                        .sorted(Comparator.comparingInt(assignments::get))
                        .toList());
        loadedAssignments.putAll(
                assignmentList.stream().collect(Collectors.toMap(Assignment::getId, a -> a)));
        return assignmentList;
    }

    public static Assignment loadAssignmentById(int id) {
        if (loadedAssignments.containsKey(id)) {
            return loadedAssignments.get(id);
        }
        String query = "SELECT * FROM catering.Assignments WHERE id = " + id + ";";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    while (rs.next()) {
                        Assignment assignment =
                                new Assignment(
                                        Procedure.loadProcedureById(rs.getInt("id_procedure")));
                        assignment.id = rs.getInt("id");
                        assignment.completed = rs.getBoolean("completed");
                        assignment.quantity = rs.getInt("quantity");
                        assignment.estimatedTime = Duration.ofMinutes(rs.getLong("estimatedTime"));
                        assignment.continuation =
                                rs.getInt("id_continuation") == 0
                                        ? null
                                        : Assignment.loadAssignmentById(
                                                rs.getInt("id_continuation"));
                        assignment.selShift =
                                rs.getInt("id_shift") == 0
                                        ? null
                                        : Shift.loadShiftById(rs.getInt("id_shift"));
                        assignment.selCook =
                                rs.getInt("id_cook") == 0
                                        ? null
                                        : User.loadUserById(rs.getInt("id_cook"));
                        loadedAssignments.put(assignment.getId(), assignment);
                    }
                });
        return loadedAssignments.get(id);
    }

    public static void saveNewAssignments(int ssId, Assignment as, int pos) {
        String InsertAssignment =
                "INSERT INTO catering.Assignments (id_summary_sheet, completed, quantity, estimatedTime, id_continuation, id_shift, id_cook, id_procedure, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PersistenceManager.executeBatchUpdate(
                InsertAssignment,
                1,
                new BatchUpdateHandler() {
                    @Override
                    public void handleBatchItem(PreparedStatement ps, int batchCount)
                            throws SQLException {
                        ps.setInt(1, ssId);
                        ps.setBoolean(2, as.isCompleted());
                        ps.setInt(3, as.getQuantity());
                        ps.setLong(4, as.getEstimatedTime().toMinutes());
                        if (as.getContinuation() == null) ps.setInt(5, 0);
                        else ps.setInt(5, as.getContinuation().getId());
                        if (as.getSelShift() == null) ps.setInt(6, 0);
                        else ps.setInt(6, as.getSelShift().getId());
                        if (as.getSelCook() == null) ps.setInt(7, 0);
                        else ps.setInt(7, as.getSelCook().getId());
                        ps.setInt(8, as.getProcedure().getId());
                        ps.setInt(9, pos - 1);
                    }

                    @Override
                    public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                        if (count == 0) {
                            as.id = rs.getInt(1);
                        }
                    }
                });
        loadedAssignments.put(as.getId(), as);
    }

    public static void updateAssignment(Assignment as) {
        String upd =
                "UPDATE Assignments SET quantity = ?, estimatedTime = ?, id_continuation = ?, id_cook = ?, id_shift = ? WHERE id = "
                        + as.getId()
                        + ";";
        PersistenceManager.executeBatchUpdate(
                upd,
                1,
                new BatchUpdateHandler() {
                    @Override
                    public void handleBatchItem(PreparedStatement ps, int batchCount)
                            throws SQLException {
                        ps.setInt(1, as.getQuantity());
                        ps.setLong(2, as.getEstimatedTime().toMinutes());
                        if (as.getContinuation() == null) ps.setInt(3, 0);
                        else ps.setInt(3, as.getContinuation().getId());
                        if (as.getSelCook() == null) ps.setInt(4, 0);
                        else ps.setInt(4, as.getSelCook().getId());
                        if (as.getSelShift() == null) ps.setInt(5, 0);
                        else ps.setInt(5, as.getSelShift().getId());
                    }

                    @Override
                    public void handleGeneratedIds(ResultSet rs, int count) {}
                });
        loadedAssignments.put(as.getId(), as);
    }

    public static void markAssignmentCompleted(SummarySheet ss, Assignment as) {
        String updateAss = "UPDATE Assignments SET completed = true WHERE id = " + as.getId() + ";";
        PersistenceManager.executeUpdate(updateAss);
        as.setCompleted(true);
        loadedAssignments.put(as.getId(), as);
    }

    public static void deleteAssignment(Assignment as) {
        String deleteAss = "DELETE FROM Assignments WHERE id = " + as.getId() + ";";
        PersistenceManager.executeUpdate(deleteAss);
        loadedAssignments.remove(as.getId());
    }

    public void setCook(User cook) throws UseCaseLogicException {
        if (selShift != null && estimatedTime != null) {
            selShift.decreaseAvailableTime(cook, this.estimatedTime);
        }
        this.selCook = cook;
    }

    public void setShift(Shift shift) throws UseCaseLogicException {
        if (selCook != null && estimatedTime != null) {
            shift.decreaseAvailableTime(this.selCook, this.estimatedTime);
        }
        this.selShift = shift;
    }

    public void setReady() {
        this.completed = true;
    }

    public Procedure getProcedure() {
        return this.itemProcedure;
    }

    public void setProcedure(Procedure itemProcedure) {
        this.itemProcedure = itemProcedure;
    }

    public boolean contains(Assignment assignment) {
        if (assignment == null) return false;
        return this.continuation == assignment || continuation.contains(assignment);
    }

    public boolean isDefined() {
        return this.selCook != null && this.selShift != null && this.estimatedTime != null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Assignment(id=").append(this.id).append(")[completed=").append(completed);
        if (itemProcedure != null) {
            builder.append(", itemProcedure=").append(itemProcedure);
        }
        if (selCook != null) {
            builder.append(", selCook=").append(selCook.getUserName());
        }
        if (selShift != null) {
            builder.append(", selShift=").append(selShift);
        }
        if (estimatedTime != Duration.ZERO) {
            builder.append(", estimatedTime=").append(estimatedTime);
        }
        if (quantity != 0) {
            builder.append(", quantity=").append(quantity);
        }
        if (continuation != null) {
            builder.append(", continuation=Assignment(id=")
                    .append(continuation.getId())
                    .append(")");
        }
        builder.append("]");

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;

        Assignment other = (Assignment) obj;
        boolean nullables = true;
        if (this.continuation == null) nullables = other.continuation == null;
        if (this.selCook == null) nullables &= other.selCook == null;
        if (this.selShift == null) nullables &= other.selShift == null;

        return this.completed == other.completed
                && this.quantity.equals(other.quantity)
                && this.estimatedTime.equals(other.estimatedTime)
                && nullables
                && this.itemProcedure.equals(other.itemProcedure)
                && this.id == other.id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Duration estimatedTime) throws UseCaseLogicException {
        if (selCook != null && selShift != null) {
            selShift.decreaseAvailableTime(this.selCook, estimatedTime);
        }
        this.estimatedTime = estimatedTime;
    }

    public Assignment getContinuation() {
        return continuation;
    }

    public void setContinuation(Assignment continuation) {
        this.continuation = continuation;
    }

    public Shift getSelShift() {
        return selShift;
    }

    public User getSelCook() {
        return selCook;
    }

    public int getId() {
        return this.id;
    }
}
