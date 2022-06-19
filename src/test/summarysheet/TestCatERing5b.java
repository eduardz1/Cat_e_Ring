package test.summarysheet;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.summarysheet.Assignment;
import main.businesslogic.summarysheet.SummarySheetException;

import java.time.Duration;
import java.util.Optional;

public class TestCatERing5b {
    public static void main(String[] args) {
        try {
            /*
             * System.out.println("TEST DATABASE CONNECTION");
             * PersistenceManager.testSQLConnection();
             */
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            ObservableList<EventInfo> Events =
                    CatERing.getInstance().getEventManager().getEventInfo();

            EventInfo event = Events.get(0);
            ServiceInfo service = event.getServices().get(0);

            System.out.println(
                    "\nTEST CREATION OF NEW SUMMARY SHEET FOR EVENT "
                            + event.getName()
                            + " AND SERVICE "
                            + service.getName());

            System.out.println(
                    CatERing.getInstance()
                            .getSummarySheetManager()
                            .createSummarySheet(service, event));
            ObservableList<Assignment> assignments =
                    CatERing.getInstance()
                            .getSummarySheetManager()
                            .getCurrentSheet()
                            .getAssignments();

            System.out.println("\nTEST DEFINING ASSIGNMENT " + assignments.get(0).getId());
            CatERing.getInstance()
                    .getSummarySheetManager()
                    .defineAssignment(
                            assignments.get(0),
                            Optional.of(3),
                            Optional.of(
                                    CatERing.getInstance().getShiftManager().getShifts().get(0)),
                            Optional.empty(),
                            Optional.of(Duration.ofMinutes(30)),
                            Optional.of(assignments.get(1)));

            System.out.println("\nTEST DELETE ASSIGNMENT " + assignments.get(0).getId());
            CatERing.getInstance().getSummarySheetManager().deleteAssignment(assignments.get(0));

            System.out.println("\nUPDATED SUMMARY SHEET:\n");
            System.out.println(CatERing.getInstance().getSummarySheetManager().getCurrentSheet());

        } catch (UseCaseLogicException | SummarySheetException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
