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

            EventInfo convegnoAgileCommunity = Events.get(0);
            ServiceInfo coffeeBreakMattina = convegnoAgileCommunity.getServices().get(0);
            ServiceInfo coffeeBreakPomeriggio = convegnoAgileCommunity.getServices().get(2);

            System.out.println(
                    "\nTEST CREATION OF NEW SUMMARY SHEET FOR EVENT "
                            + convegnoAgileCommunity.getName()
                            + " AND SERVICE "
                            + coffeeBreakMattina.getName());
            System.out.println(
                    "\nTEST CREATION OF NEW SUMMARY SHEET FOR EVENT "
                            + convegnoAgileCommunity.getName()
                            + " AND SERVICE "
                            + coffeeBreakPomeriggio.getName());

            SummarySheet ss1 =
                    CatERing.getInstance()
                            .getSummarySheetManager()
                            .createSummarySheet(coffeeBreakMattina, convegnoAgileCommunity);
            SummarySheet ss2 =
                    CatERing.getInstance()
                            .getSummarySheetManager()
                            .createSummarySheet(coffeeBreakPomeriggio, convegnoAgileCommunity);

            System.out.println(
                    "\nTEST GETTING SUMMARY SHEET FOR EVENT "
                            + convegnoAgileCommunity.getName()
                            + " AND SERVICE "
                            + coffeeBreakMattina.getName());
            CatERing.getInstance().getSummarySheetManager().chooseSummarySheet(ss1);
            SummarySheet expected1 =
                    CatERing.getInstance().getSummarySheetManager().getCurrentSheet();

            System.out.println(
                    "\tEXPECTED RESULT: currentSheet updated to the first one\n\n" + expected1);

            System.out.println(
                    "\nTEST GETTING SUMMARY SHEET FOR EVENT "
                            + convegnoAgileCommunity.getName()
                            + " AND SERVICE "
                            + coffeeBreakPomeriggio.getName());
            CatERing.getInstance().getSummarySheetManager().chooseSummarySheet(ss2);
            SummarySheet expected2 =
                    CatERing.getInstance().getSummarySheetManager().getCurrentSheet();

            System.out.println(
                    "\tEXPECTED RESULT: currentSheet updated to the second one\n\n" + expected2);
        } catch (UseCaseLogicException | SummarySheetException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
