package main.businesslogic.summarysheet;

import main.businesslogic.procedure.Procedure;

public interface SummarySheetEventReceiver {

    void updateProcedureAdded(SummarySheet ss, Assignment as);

    void updateAssignmentDefined(SummarySheet ss, Assignment as);

    void updateAssignmentCompleted(SummarySheet ss, Assignment as);

    void updateSummarySheetCreated(SummarySheet ss);

    void updateAssignmentRearranged(Assignment as);

    void updateAssignmentModified(SummarySheet ss, Assignment as);

	void updateProcedureRemoved(SummarySheet ss, Procedure pro);
}