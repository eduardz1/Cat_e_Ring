package main.businesslogic.summarysheet;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.Event;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.menu.MenuEventReceiver;
import main.businesslogic.menu.MenuException;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.shift.Shift;
import main.businesslogic.user.User;

/**
 * SummarySheetManager
 */
public class SummarySheetManager {

    private SummarySheet currentSheet;
    
    public SummarySheet getCurrentSheet() {
        return currentSheet;
    }

    private final ArrayList<SummarySheetEventReceiver> eventReceivers;

    public SummarySheetManager() {
        this.eventReceivers = new ArrayList<>();
    }

    private void setCurrentSummarySheet(SummarySheet sheet) {
        this.currentSheet = sheet;
    }

    public Assignment addProcedure(Procedure pro) throws UseCaseLogicException {
        if(this.currentSheet == null) {
            throw new UseCaseLogicException("addProcedure: " + "No current sheet");
        }
        
        Assignment as = this.currentSheet.addAssignment(pro);
        this.notifyProcedureAdded(as);
        return as;
    }

    public void removeProcedure(Procedure pro) throws UseCaseLogicException, SummarySheetException {
        if(this.currentSheet == null) {
            throw new UseCaseLogicException("getShiftBoard: " + "No current sheet");
        }
        if(!CatERing.getInstance().getUserManager().getCurrentUser().isChef()) {
            throw new UseCaseLogicException("removeProcedure: " + "Only chefs can remove procedures");
        }
        if(!currentSheet.isAssigned(pro)) {
            throw new SummarySheetException("removeProcedure: " + "Cannot remove assigned procedure");
        }
        currentSheet.removeProcedure(pro); // FIXME check return if necessary
        this.notifyProcedureRemoved(pro);
    }

    public void deleteAssignment(Assignment as) throws UseCaseLogicException, SummarySheetException {
        if(this.currentSheet == null) {
            throw new UseCaseLogicException("deleteAssignment: " + "No current sheet");
        }
        if(!currentSheet.getAssignments().contains(as)){
            throw new SummarySheetException("deleteAssignment: " + "No assignment in array");
        }
        currentSheet.deleteAssignment(as);
        this.notifyAssignmentRemoved(as);
    }

    public Assignment defineAssignment(Assignment assignment,
                                       Optional<Integer> quantity,
                                       Optional<Shift> shift,
                                       Optional<User> cook,
                                       Optional<Duration> estimatedTime,
                                       Optional<Assignment> continuation) throws UseCaseLogicException {
        if (this.currentSheet == null) {
            throw new UseCaseLogicException("defineAssignment: " + "currentSheet was not initialized");
        }
        if (!this.currentSheet.hasAssignment(assignment)) {
            throw new UseCaseLogicException("defineAssignment: " + "currentSheet does not contain assignment");
        }
        if (continuation.isPresent() && !this.currentSheet.hasAssignment(continuation.get())) {
            throw new UseCaseLogicException("defineAssignment: " + "currentSheet does not contain continuation");
        }
        if (continuation.isPresent() && continuation.get().equals(assignment)) {
            throw new UseCaseLogicException("defineAssignment: " + "continuation can't be equal to main assignment");
        }
        if (cook.isPresent() && !cook.get().isCook()) {
            throw new UseCaseLogicException("defineAssignment: " + "user is not a cook");
        }
        if (shift.isPresent() && (shift.get().getDate().isBefore(LocalDate.now()))) {
            throw new UseCaseLogicException("defineAssignment: " + "shift date is in the past");
        }

        Assignment as = this.currentSheet.defineAssignment(assignment, quantity, shift, cook, estimatedTime, continuation);
        notifyAssignmentDefined(as);
        return as;
    }

    public void procedureReady(Assignment as) throws UseCaseLogicException, SummarySheetException {
        if(this.currentSheet == null) {
            throw new UseCaseLogicException("procedureReady: " + "No current sheet");
        }
        if(!this.currentSheet.hasAssignment(as)){
            throw new SummarySheetException("procedureReady: " + "No assignment in current Sheet");
        }
        currentSheet.assignmentCompleted(as);
        this.notifyAssignmentCompleted(as);
    }

    public SummarySheet createSummarySheet(ServiceInfo service, EventInfo event) throws UseCaseLogicException, SummarySheetException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef())
            throw new UseCaseLogicException();
        if (event.getOrganizer() != user)
            throw new SummarySheetException();
        
        SummarySheet sheet = new SummarySheet(service);
        this.setCurrentSummarySheet(sheet);
        this.notifySummarySheetAdded(sheet);

        return sheet;
    }

    public void changeAssignmentOrder(Assignment as, int position) throws UseCaseLogicException, SummarySheetException {
        if(this.currentSheet == null) {
            throw new UseCaseLogicException("removeProcedure: " + "No current sheet");
        }
        if(!CatERing.getInstance().getUserManager().getCurrentUser().isChef()) {
            throw new UseCaseLogicException("changeAssignmentOrder: " + "Only chefs can change assignment order");
        }
        if(!currentSheet.hasAssignment(as)) {
            throw new SummarySheetException("changeAssignmentOrder: " + "Summary Sheet does not contain assignment");
        }
        if (position < 0 || position >= currentSheet.getAssignments().size()) {
            throw new IllegalArgumentException();
        }

        this.currentSheet.moveAssignments(as, position);
        this.notifyAssignmentRearranged();
    }

    public ArrayList<Shift> getShiftBoard() throws UseCaseLogicException {
        if(this.currentSheet == null) {
            throw new UseCaseLogicException("changeAssignmentOrder: " + "No current sheet");
        }
        if(!CatERing.getInstance().getUserManager().getCurrentUser().isChef()) {
            throw new UseCaseLogicException("getShiftBoard: " + "Only chefs can get shift board");
        }

        return CatERing.getInstance().getShiftManager().getShifts();
    }

    public void chooseSummarySheet(SummarySheet ss) throws UseCaseLogicException, SummarySheetException {
        User u = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!u.isChef())
            throw new UseCaseLogicException("chooseSummarySheet: " + "only chefs can perform this operation");
        if (ss.getOwner() != u) {
            throw new SummarySheetException("chooseSummarySheet: " + "only the owner can select this summary sheet");
        }

        this.currentSheet = ss;
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

    private void notifyAssignmentRearranged() {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignmentRearranged(this.currentSheet);
        }
    }

    private void notifyAssignmentRemoved(Assignment as) {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateAssignmentRemoved(currentSheet, as);
        }
    }

    private void notifyProcedureRemoved(Procedure pro) {
        for (SummarySheetEventReceiver eventReceiver : eventReceivers) {
            eventReceiver.updateProcedureRemoved(currentSheet, pro);
        }
    }

    public void addEventReceiver(SummarySheetEventReceiver rec) {
        this.eventReceivers.add(rec);
    }

    public void removeEventReceiver(MenuEventReceiver rec) {
        this.eventReceivers.remove(rec);
    }
}