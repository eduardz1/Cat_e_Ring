package main.businesslogic.summarysheet;

import java.time.LocalTime;

import main.businesslogic.procedure.Procedure;
import main.businesslogic.shift.Cook;
import main.businesslogic.shift.Shift;

public class Assignment {

    private boolean completed;
    private String quantity;
    private LocalTime estimatedTime;
    private Assignment continuation;
    private Shift selShift;
    private Cook selCook;
    private Procedure itemProcedure;
    
    
    public Assignment(Procedure procedure) {
        this.itemProcedure = procedure;
        this.completed = false;
        this.quantity = "";
        this.estimatedTime = null;
        this.continuation = null;
        this.selShift = null;
        this.selCook = null;
    }

    public void setCook(Cook cook) {
        this.selCook = cook;
    }

    public void setTime(LocalTime time) {
        this.estimatedTime = time;
    }

    public void setQuantity(String quantity) // FIXME: check whether it should be String or Integer
    {
        this.quantity = quantity;
    }
    
    public void setShift(Shift shift) {
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
    
    // public boolean contains(Assignment assignment) { // FIXME
        // return false;
        // }
        
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
        
    public String getQuantity() {
        return quantity;
    }
    
    public LocalTime getEstimatedTime() {
        return estimatedTime;
    }
    
    public Assignment getContinuation() {
        return continuation;
    }
    
    public Shift getSelShift() {
        return selShift;
    }
    
    public Cook getSelCook() {
        return selCook;
    }
    
    public Procedure getItemProcedure() {
        return itemProcedure;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setEstimatedTime(LocalTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setContinuation(Assignment continuation) {
        this.continuation = continuation;
    }

    public void setSelShift(Shift selShift) {
        this.selShift = selShift;
    }

    public void setSelCook(Cook selCook) {
        this.selCook = selCook;
    }

    public void setItemProcedure(Procedure itemProcedure) {
        this.itemProcedure = itemProcedure;
    }
}
