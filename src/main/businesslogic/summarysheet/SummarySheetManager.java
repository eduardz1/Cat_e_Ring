package main.businesslogic.summarysheet;

import java.security.Provider.Service;
import java.time.LocalTime;
import java.util.ArrayList;

import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.Event;
import main.businesslogic.menu.MenuException;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.shift.Cook;
import main.businesslogic.shift.Shift;
import main.businesslogic.user.User;

/**
 * SummarySheetManager
 */
public class SummarySheetManager {

    private SummarySheet currentSheet;
    private final ArrayList<SummarySheetEventReceiver> eventReceivers;

    public SummarySheetManager() {
        this.eventReceivers = new ArrayList<>();
    }

    public void addProcedure(Procedure pro) {
        // TODO
    }

    public void removeProcedure(Procedure pro) {
        // TODO
    }

    public void defineAssignment(Assignment as, String quantity, Shift shift, Cook cook, LocalTime time,
            Assignment cont) {
        // TODO
        // TODO check wheter the cook has enough time with the modified assignment,
        // update the previous cook to reflect the new time he has
    }

    // #region defineAssignment
    public void defineAssignment(Assignment as, String quantity, Shift shift) { // only mandatory arguments
        this.defineAssignment(as, quantity, shift, null, null, null);
    }

    public void defineAssignment(Assignment as, String quantity, Shift shift, Cook cook) {
        this.defineAssignment(as, quantity, shift, cook, null, null);
    }

    public void defineAssignment(Assignment as, String quantity, Shift shift, LocalTime time) {
        this.defineAssignment(as, quantity, shift, null, time, null);
    }

    public void defineAssignment(Assignment as, String quantity, Shift shift, Cook cook, LocalTime time) {
        this.defineAssignment(as, quantity, shift, cook, time, null);
    }

    public void defineAssignment(Assignment as, String quantity, Shift shift, Assignment cont) {
        this.defineAssignment(as, quantity, shift, null, null, cont);
    }

    public void defineAssignment(Assignment as, String quantity, Shift shift, Cook cook, Assignment cont) {
        this.defineAssignment(as, quantity, shift, cook, null, cont);
    }

    public void defineAssignment(Assignment as, String quantity, Shift shift, LocalTime time, Assignment cont) {
        this.defineAssignment(as, quantity, shift, null, time, cont);
    }
    // #endregion

    public void modifyAssignment(Assignment as, String quantity, Shift shift, Cook cook, LocalTime time) {
        // TODO
    }

    // #region modifyAssignment
    public void modifyAssignment(Assignment as, String quantity, Shift shift, Cook cook) {
        this.modifyAssignment(as, quantity, shift, cook, null);
    }

    public void modifyAssignment(Assignment as, String quantity, Shift shift, LocalTime time) {
        this.modifyAssignment(as, quantity, shift, null, time);
    }
    // #endregion

    public void procedureReady(Assignment as) {
        // TODO
    }

    public SummarySheet createSummarySheet(Service service, Event event) throws UseCaseLogicException, SummarySheetException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef())
            throw new UseCaseLogicException();
        if (this.currentSheet == null)
            throw new UseCaseLogicException();
        if (event.assignedTo() != user)
            throw new SummarySheetException();
        
        SummarySheet sheet = new SummarySheet(service, event);
        this.setCurrentSummarySheet(sheet);
        this.notifySummarySheetAdded(sheet);

        return sheet;
    }

    private void setCurrentSummarySheet(SummarySheet sheet) {
        this.currentSheet = sheet;
    }

    public void seCurrenttSummarySheet(SummarySheet sheet) {
        this.currentSheet = sheet;
    }

    public void changeAssignmentOrder(Assignment as, Assignment cont) {
        // TODO
    }

    public ArrayList<Shift> getShiftBoard() {
        // TODO
        return null;
    }

    // Event sender methods

    private void notifyProcedureAdded(Assignment as) {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateProcedureAdded(currentSheet, as);
        }
    }

    private void notifyAssignmentDefined(Assignment as) {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignmentDefined(currentSheet, as);
        }
    }

    private void notifyAssignmentCompleted(Assignment as) {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignmentCompleted(currentSheet, as);
        }
    }

    private void notifySummarySheetAdded(SummarySheet ss) {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateSummarySheetCreated(ss);
        }
    }

    private void notifyAssignmentRearranged(SummarySheet ss) {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignmentRearranged(ss);
        }
    }

    private void notifyAssignmentModified(Assignment as) {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignmentModified(currentSheet, as);
        }
    }
}