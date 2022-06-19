package test.summarysheet;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.summarysheet.Assignment;
import main.businesslogic.summarysheet.SummarySheetException;
import main.businesslogic.user.User;

import java.time.Duration;
import java.util.Optional;

public class TestCatERing5 {
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

            System.out.println(
                    "\nTEST DEFINING ASSIGNMENT id="
                            + assignments.get(0).getId()
                            + ", quantity = 3, shift_id = "
                            + CatERing.getInstance().getShiftManager().getShifts().get(0).getId()
                            + ", duration = 30, continuation_id = "
                            + assignments.get(1).getId()
                            + ", user = "
                            + User.loadUserById(4).getUserName()
                            + "\n\tEXPECTED RESULT: UseCaseLogicException");
            try {
                CatERing.getInstance()
                        .getSummarySheetManager()
                        .defineAssignment(
                                assignments.get(0),
                                Optional.of(3),
                                Optional.of(
                                        CatERing.getInstance()
                                                .getShiftManager()
                                                .getShifts()
                                                .get(0)),
                                Optional.of(User.loadUserById(4)),
                                Optional.of(Duration.ofMinutes(30)),
                                Optional.of(assignments.get(1)));
            } catch (UseCaseLogicException e) {
                System.out.println(">>> UseCaseLogicException::" + e.getMessage());
            }

            System.out.println(
                    "\nTEST DEFINING ASSIGNMENT id="
                            + assignments.get(0).getId()
                            + ", quantity = 3, shift_id = "
                            + CatERing.getInstance().getShiftManager().getShifts().get(2).getId()
                            + ", duration = 30, continuation_id = "
                            + assignments.get(1).getId()
                            + ", user = "
                            + User.loadUserById(4).getUserName()
                            + "\n\tEXPECTED RESULT: SUCCESS");
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
