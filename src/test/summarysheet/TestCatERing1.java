package test.summarysheet;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.menu.Menu;
import main.businesslogic.summarysheet.SummarySheet;
import main.businesslogic.summarysheet.SummarySheetException;

import java.util.ArrayList;

public class TestCatERing1 {
    public static void main(String[] args) {
        try {
            /*
             * System.out.println("TEST DATABASE CONNECTION");
             * PersistenceManager.testSQLConnection();
             */
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            ObservableList<Menu> boh = CatERing.getInstance().getMenuManager().getAllMenus();

            System.out.println(boh.get(0).getId());
            System.out.println(boh.get(0).getFreeItems());
            System.out.println(boh.get(0).getAllItems());

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
        } catch (UseCaseLogicException | SummarySheetException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
