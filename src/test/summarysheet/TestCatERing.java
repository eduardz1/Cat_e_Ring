package test.summarysheet;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.summarysheet.SummarySheet;
import main.businesslogic.summarysheet.SummarySheetException;
import main.businesslogic.user.User;

import java.time.Duration;
import java.util.Optional;

public class TestCatERing {
    public static void main(String[] args) {
        try {
            /*
             * System.out.println("TEST DATABASE CONNECTION");
             * PersistenceManager.testSQLConnection();
             */
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println("\n" + CatERing.getInstance().getUserManager().getCurrentUser());
            ObservableList<EventInfo> Events =
                    CatERing.getInstance().getEventManager().getEventInfo();

            EventInfo event = Events.get(0);
            ServiceInfo service = event.getServices().get(0);

            // -------------------------------------------------------------------------------------
            // 1. Create a new SummarySheet
            // -------------------------------------------------------------------------------------
            System.out.println(
                    "\nCREATION OF NEW SUMMARY SHEET FOR EVENT \""
                            + event.getName()
                            + "\" AND SERVICE "
                            + service.getName());

            SummarySheet ss =
                    CatERing.getInstance()
                            .getSummarySheetManager()
                            .createSummarySheet(service, event);

            System.out.println(ss);

            var procedures = CatERing.getInstance().getProcedureManager().getAllProcedures();
            System.out.println("ALL POSSIBLE PROCEDURES TO BE ADDED:\n" + procedures);

            // -------------------------------------------------------------------------------------
            // 2. Optionally add a new Assignment
            // -------------------------------------------------------------------------------------

            System.out.println("\nADDING PROCEDURE \"" + procedures.get(4).getName() + "\"");
            CatERing.getInstance().getSummarySheetManager().addProcedure(procedures.get(4));

            System.out.println("UPDATED SUMMARY SHEET:\n" + ss);

            // -------------------------------------------------------------------------------------
            // 3. Optionally change Assignment order
            // -------------------------------------------------------------------------------------
            System.out.println("\nREORDERING THE ASSIGNMENTS: 8th -> 1st");
            CatERing.getInstance()
                    .getSummarySheetManager()
                    .changeAssignmentOrder(
                            CatERing.getInstance()
                                    .getSummarySheetManager()
                                    .getCurrentSheet()
                                    .getAssignments()
                                    .get(7),
                            0);

            System.out.println(ss);

            // -------------------------------------------------------------------------------------
            // 4. Optionally ask for shift board
            // -------------------------------------------------------------------------------------
            var shiftBoard = CatERing.getInstance().getSummarySheetManager().getShiftBoard();
            System.out.println("\nASKING FOR SHIFT BOARD:\n" + shiftBoard);

            // -------------------------------------------------------------------------------------
            // 5. Define an assignment
            // -------------------------------------------------------------------------------------
            var assignments = ss.getAssignments();
            System.out.println(
                    "\nTEST DEFINING Assignment id="
                            + assignments.get(0).getId()
                            + ", quantity = 3, shift_id = "
                            + CatERing.getInstance().getShiftManager().getShifts().get(2).getId()
                            + ", duration = 30, continuation_id = "
                            + assignments.get(1).getId()
                            + ", user = "
                            + User.loadUserById(4).getUserName());
            CatERing.getInstance()
                    .getSummarySheetManager()
                    .defineAssignment(
                            assignments.get(0),
                            Optional.of(3),
                            Optional.of(
                                    CatERing.getInstance().getShiftManager().getShifts().get(2)),
                            Optional.of(User.loadUserById(4)),
                            Optional.of(Duration.ofMinutes(30)),
                            Optional.of(assignments.get(1)));

            System.out.println(
                    "\nUPDATED SUMMARY SHEET:\n"
                            + CatERing.getInstance().getSummarySheetManager().getCurrentSheet());
        } catch (UseCaseLogicException | SummarySheetException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
