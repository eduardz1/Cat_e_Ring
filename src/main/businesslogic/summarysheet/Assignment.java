package main.businesslogic.summarysheet;

import java.time.Duration;
import java.util.List;

import main.businesslogic.UseCaseLogicException;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.shift.Shift;
import main.businesslogic.user.User;

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
    
    public Procedure getItemProcedure() {
        return itemProcedure;
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
        // TODO implement
    }
}
