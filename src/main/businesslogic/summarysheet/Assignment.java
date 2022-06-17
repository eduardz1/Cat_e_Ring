package main.businesslogic.summarysheet;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

import main.businesslogic.UseCaseLogicException;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.shift.Shift;
import main.businesslogic.user.User;
import main.persistence.BatchUpdateHandler;
import main.persistence.PersistenceManager;

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
        this.estimatedTime = null;
        this.continuation = null;
        this.selShift = null;
        this.selCook = null;
    }


    public void setCook(User cook) throws UseCaseLogicException {
        if (selShift != null && estimatedTime != null) {
            selShift.decreaseAvailableTime(cook, this.estimatedTime);
        }
        this.selCook = cook;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
    
    public void setContinue(Assignment assignment) {
        this.continuation = assignment;
    }
    
    public Procedure getProcedure() {
        return this.itemProcedure;
    }
    
    public boolean contains(Assignment assignment) {
        if(assignment == null) return false;
        return this.continuation == assignment || this.continuation.contains(assignment);
    }

    public boolean isDefined() {
        return this.selCook != null &&
                this.selShift != null &&
                this.estimatedTime != null;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "completed=" + completed +
                ", quantity=" + quantity +
                ", estimatedTime=" + estimatedTime +
                ", continuation=" + continuation +
                ", selShift=" + selShift +
                ", selCook=" + selCook +
                ", itemProcedure=" + itemProcedure +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Assignment other = (Assignment) obj;
        return this.isCompleted() == other.isCompleted() &&
            this.getQuantity().equals(other.getQuantity()) &&
            this.getEstimatedTime().equals(other.getEstimatedTime()) &&
            this.getContinuation().equals(other.getContinuation()) &&
            this.getSelShift().equals(other.getSelShift()) &&
            this.getSelCook().equals(other.getSelCook()) &&
            this.getProcedure().equals(other.getProcedure());
    }

    public boolean isCompleted() {
            return completed;
        }
        
    public Integer getQuantity() {
        return quantity;
    }
    
    public Duration getEstimatedTime() {
        return estimatedTime;
    }
    
    public Assignment getContinuation() {
        return continuation;
    }
    
    public Shift getSelShift() {
        return selShift;
    }
    
    public User getSelCook() {
        return selCook;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setEstimatedTime(Duration estimatedTime) throws UseCaseLogicException {
        if (selCook != null && selShift != null) {
            selShift.decreaseAvailableTime(this.selCook, estimatedTime);
        }
        this.estimatedTime = estimatedTime;
    }

    public void setContinuation(Assignment continuation) {
        this.continuation = continuation;
    }

    public void setSelShift(Shift selShift) {
        this.selShift = selShift;
    }

    public void setItemProcedure(Procedure itemProcedure) {
        this.itemProcedure = itemProcedure;
    }

    public int getId() {
        return this.id;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static void saveAllNewAssignments(int summarysheet_id, List<Assignment> assignments) {
        String AssInsert = "INSERT INTO catering.assignments (completed, quantity, estimatedTime, id_as_continuation, id_shift, id_cook, id_Procedure) VALUES (?, ?, ?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(AssInsert, assignments.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setBoolean(1, assignments.get().isCompleted());
                ps.setInt(2, summarysheet_id);
                ps.setInt(3, as.getQuantity());
                ps.setLong(4, (int) as.getEstimatedTime().toMinutes());
                ps.setInt(5, as.getContinuation().getId());
                ps.setInt(6, as.getSelShift().getId());
                ps.setInt(7, as.getSelCook().getId());
                ps.setInt(8, as.getProcedure().getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

            }
        });
    }

    public static void saveNewAssignments(int ssId, Assignment as) {
        String InsertAssignment = "INSERT INTO catering.assignments (completed, quantity, estimatedTime, id_as_continuation, id_shift, id_cook, id_Procedure) VALUES (?, ?, ?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(InsertAssignment, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
              ps.setBoolean(1, as.isCompleted());
              ps.setInt(2, ssId);
              ps.setInt(3, as.getQuantity());
              ps.setLong(4, (int) as.getEstimatedTime().toMinutes());
              ps.setInt(5, as.getContinuation().getId());
              ps.setInt(6, as.getSelShift().getId());
              ps.setInt(7, as.getSelCook().getId());
              ps.setInt(8, as.getProcedure().getId());
            }
            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
               if(count == 0)
               {
                   as.id=rs.getInt(1);
               }
            }
        });
    }
}
