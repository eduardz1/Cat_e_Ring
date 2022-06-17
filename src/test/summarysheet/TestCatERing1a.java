package test.summarysheet;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.summarysheet.SummarySheet;
import main.businesslogic.summarysheet.SummarySheetException;

public class TestCatERing1a {

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

            EventInfo event1 = Events.get(0);
            EventInfo event2 = Events.get(1);
            ServiceInfo service1 = event1.getServices().get(0);
            ServiceInfo service2 = event2.getServices().get(0);

            System.out.println(
                    "TEST CREATION OF NEW SUMMARY SHEET FOR EVENT "
                            + event1.getName()
                            + " AND SERVICE "
                            + service1.getName());
            System.out.println(
                    "TEST CREATION OF NEW SUMMARY SHEET FOR EVENT "
                            + event2.getName()
                            + " AND SERVICE "
                            + service2.getName());

            SummarySheet ss1 =
                    CatERing.getInstance()
                            .getSummarySheetManager()
                            .createSummarySheet(service1, event1);
            SummarySheet ss2 =
                    CatERing.getInstance()
                            .getSummarySheetManager()
                            .createSummarySheet(service2, event2);

            System.out.println(
                    "TEST GETTING SUMMARY SHEET FOR EVENT "
                            + event1.getName()
                            + " AND SERVICE "
                            + service1.getName());
            CatERing.getInstance().getSummarySheetManager().chooseSummarySheet(ss1);
            SummarySheet expected1 =
                    CatERing.getInstance().getSummarySheetManager().getCurrentSheet();

            System.out.println("Expected summary sheet for first event:\n" + expected1);

            System.out.println(
                    "TEST GETTING SUMMARY SHEET FOR EVENT "
                            + event2.getName()
                            + " AND SERVICE "
                            + service2.getName());
            CatERing.getInstance().getSummarySheetManager().chooseSummarySheet(ss2);
            SummarySheet expected2 =
                    CatERing.getInstance().getSummarySheetManager().getCurrentSheet();

            System.out.println("Expected summary sheet for second event:\n" + expected2);
        } catch (UseCaseLogicException | SummarySheetException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
