package main.persistence;

import main.businesslogic.procedure.Procedure;
import main.businesslogic.summarysheet.Assignment;
import main.businesslogic.summarysheet.SummarySheet;
import main.businesslogic.summarysheet.SummarySheetEventReceiver;

public class SummarySheetPersistence implements SummarySheetEventReceiver {
    @Override
    public void updateProcedureAdded(SummarySheet ss, Assignment as) {
        Assignment.saveNewAssignments(ss.getId(), as, ss.getAssignments().size());
    }

    @Override
    public void updateAssignmentDefined(SummarySheet ss, Assignment as) {
        Assignment.updateAssignment(as);
    }

    @Override
    public void updateAssignmentCompleted(SummarySheet ss, Assignment as) {
        Assignment.markAssignmentCompleted(ss, as);
    }

    @Override
    public void updateSummarySheetCreated(SummarySheet ss) {
        SummarySheet.saveNewSummarySheet(ss);
    }

    @Override
    public void updateAssignmentRearranged(SummarySheet ss) {
        SummarySheet.saveAssignmentsOrder(ss);
    }

    @Override
    public void updateProcedureRemoved(SummarySheet ss, Procedure pro) {
        for (Assignment assignment : ss.getAssignments()) {
            if (assignment.getProcedure().equals(pro)) {
                Assignment.deleteAssignment(assignment);
            }
        }
    }

    @Override
    public void updateAssignmentRemoved(SummarySheet currentSheet, Assignment as) {
        Assignment.deleteAssignment(as);
    }
}
