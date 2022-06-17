package test.SummarySheet;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.menu.Menu;
import main.businesslogic.menu.Section;
import main.businesslogic.procedure.Recipe;

public class TestCatERing1a {
    public static void main(String[] args) {
        try {
            /*
             * System.out.println("TEST DATABASE CONNECTION");
             * PersistenceManager.testSQLConnection();
             */
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            ObservableList<EventInfo> Events = CatERing.getInstance().getEventManager().getEventInfo();

            EventInfo event = Events.get(0);
            ServiceInfo service = event.getServices().get(0);

            System.out.println("TEST CREATION OF NEW SUMMARY SHEET FOR EVENT " + event.getName() + " AND SERVICE " + service.getName());
            
            SummarySheet ss = CatERing.getInstance().getSummarySheetManager().createSummarySheet(service, event);

            System.out.println(ss);

            System.out.println("TEST DELETION OF SUMMARY SHEET");
            CatERing.getInstance().getSummarySheetManager().deleteSummarySheet(ss);

        } catch (UseCaseLogicException | SummarySheetException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
