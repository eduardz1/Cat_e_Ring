package test.summarysheet;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.summarysheet.SummarySheet;
import main.businesslogic.summarysheet.SummarySheetException;

public class TestCatERing1 {
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

            System.out.println(
                    "\nTEST CREATION OF NEW SUMMARY SHEET FOR EVENT "
                            + event.getName()
                            + " AND SERVICE "
                            + service.getName());

            SummarySheet ss =
                    CatERing.getInstance()
                            .getSummarySheetManager()
                            .createSummarySheet(service, event);

            System.out.println(ss);

            CatERing.getInstance().getUserManager().fakeLogin("Piergiorgio");
            System.out.println("\n" + CatERing.getInstance().getUserManager().getCurrentUser());

            event = Events.get(2);
            service = event.getServices().get(0);

            System.out.println(
                    "\nTEST CREATION OF NEW SUMMARY SHEET FOR EVENT "
                            + event.getName()
                            + " AND SERVICE "
                            + service.getName());
            System.out.println("\tEXPECTED RESULT: exception thrown, Piergiorgio is not a chef");

            try {
                ss =
                        CatERing.getInstance()
                                .getSummarySheetManager()
                                .createSummarySheet(service, event);
            } catch (UseCaseLogicException e) {
                System.out.println(">>> UseCaseLogicException::" + e.getMessage());
            }

            CatERing.getInstance().getUserManager().fakeLogin("Marinella");
            System.out.println("\n" + CatERing.getInstance().getUserManager().getCurrentUser());

            System.out.println(
                    "\nTEST CREATION OF NEW SUMMARY SHEET FOR EVENT "
                            + event.getName()
                            + " AND SERVICE "
                            + service.getName());
            System.out.println(
                    "\tEXPECTED RESULT: exception thrown, Marinella is not the organizer");

            try {
                ss =
                        CatERing.getInstance()
                                .getSummarySheetManager()
                                .createSummarySheet(service, event);
            } catch (SummarySheetException e) {
                System.out.println(">>> SummarySheetException::" + e.getMessage());
            }

            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println("\n" + CatERing.getInstance().getUserManager().getCurrentUser());
            System.out.println("\nLOADING ALL SUMMARY SHEETS IN THE DATABASE:");
            System.out.println(CatERing.getInstance().getSummarySheetManager().getAllSheets());
        } catch (UseCaseLogicException | SummarySheetException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
