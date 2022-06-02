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


public Assignment (Procedure procedure)
{
    
}

public void setCook(Cook cook)
{
  this.selCook=cook;
}

public void setTime(LocalTime time)
{
   this.estimatedTime=time;
} 

public void setQuantity(String quantity) // FIXME: check whether it should be String or Integer
{
  this.quantity=quantity;
}

public void setShift(Shift shift)
{
   this.selShift=shift;
}

public void setReady()
{
   this.completed=true;
}

public void setContinue (Assignment assignment)
{
   this.continuation=assignment;
}
}
