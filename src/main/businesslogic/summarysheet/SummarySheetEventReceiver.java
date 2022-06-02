package main.businesslogic.summarysheet;

public interface SummarySheetEventReceiver {

    void updateProcedureAdded(SummarySheet ss, Assignment as);

    void updateAssignmentDefined(SummarySheet ss, Assignment as);

    void updateAssignmentCompleted(SummarySheet ss, Assignment as);

    void updateSummarySheetCreated(SummarySheet ss);

    void updateAssignmentRearranged(SummarySheet ss);

    void updateAssignmentModified(SummarySheet ss, Assignment as);
}