package main.businesslogic.summarysheet;

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
import java.util.List;

public class Assignment {

    private boolean completed;
    private Integer quantity;
    private Duration estimatedTime;
    private Assignment continuation;
    private Shift selShift;
    private User selCook;
    private Procedure itemProcedure;

    private int id; // FIXME initialize this

    public Assignment(Procedure procedure) {
        this.id = 0;
        this.itemProcedure = procedure;
        this.completed = false;
        this.quantity = 0;
        this.estimatedTime = Duration.ofSeconds(0);
        this.continuation = null;
        this.selShift = null;
        this.selCook = null;
    }

    public static void saveAllNewAssignments(int summarysheet_id, List<Assignment> assignments) {
        String AssInsert =
                "INSERT INTO catering.Assignments (id_summary_sheet, completed, quantity, estimatedTime, id_continuation, id_shift, id_cook, id_Procedure) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        int[] result =
                PersistenceManager.executeBatchUpdate(
                        AssInsert,
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
                                        (int)
                                                assignments
                                                        .get(batchCount)
                                                        .getEstimatedTime()
                                                        .toMinutes());
                                ps.setInt(5, 0);
                                ps.setInt(6, 0);
                                ps.setInt(7, 0);
                                ps.setInt(8, assignments.get(batchCount).getProcedure().getId());
                            }

                            @Override
                            public void handleGeneratedIds(ResultSet rs, int count)
                                    throws SQLException {
                                assignments.get(count).id = rs.getInt(1);
                            }
                        });
    }

    public static void saveNewAssignments(int ssId, Assignment as) {
        String InsertAssignment =
                "INSERT INTO catering.Assignments (completed, quantity, estimatedTime, id_continuation, id_shift, id_cook, id_Procedure) VALUES (?, ?, ?, ?, ?, ?, ?);";
        int[] result =
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
                                ps.setLong(4, (int) as.getEstimatedTime().toMinutes());
                                ps.setInt(5, as.getContinuation().getId());
                                ps.setInt(6, as.getSelShift().getId());
                                ps.setInt(7, as.getSelCook().getId());
                                ps.setInt(8, as.getProcedure().getId());
                            }

                            @Override
                            public void handleGeneratedIds(ResultSet rs, int count)
                                    throws SQLException {
                                if (count == 0) {
                                    as.id = rs.getInt(1);
                                }
                            }
                        });
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
        return this.continuation == assignment || this.continuation.contains(assignment);
    }

    public boolean isDefined() {
        return this.selCook != null && this.selShift != null && this.estimatedTime != null;
    }

    @Override
    public String toString() {
        return "Assignment{"
                + "completed="
                + completed
                + ", quantity="
                + quantity
                + ", estimatedTime="
                + estimatedTime
                + ", continuation="
                + continuation
                + ", selShift="
                + selShift
                + ", selCook="
                + selCook
                + ", itemProcedure="
                + itemProcedure
                + ", id="
                + id
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Assignment other = (Assignment) obj;
        return this.isCompleted() == other.isCompleted()
                && this.getQuantity().equals(other.getQuantity())
                && this.getEstimatedTime().equals(other.getEstimatedTime())
                && this.getContinuation().equals(other.getContinuation())
                && this.getSelShift().equals(other.getSelShift())
                && this.getSelCook().equals(other.getSelCook())
                && this.getProcedure().equals(other.getProcedure());
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

    // STATIC METHODS FOR PERSISTENCE

    public User getSelCook() {
        return selCook;
    }

    public int getId() {
        return this.id;
    }
}
