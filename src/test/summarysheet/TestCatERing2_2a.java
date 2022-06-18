package test.summarysheet;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.ServiceInfo;
import main.businesslogic.procedure.Procedure;
import main.businesslogic.summarysheet.SummarySheetException;

public class TestCatERing2_2a {
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

            Procedure procedure = CatERing.getInstance().getProcedureManager().getRecipes().get(0);
            System.out.println("\nTEST ADDING PROCEDURE " + procedure + " TO SUMMARY SHEET");
            CatERing.getInstance().getSummarySheetManager().addProcedure(procedure);

            System.out.println(CatERing.getInstance().getSummarySheetManager().getCurrentSheet());

            System.out.println("\nTEST REMOVING PROCEDURE " + procedure + " FROM SUMMARY SHEET");
            CatERing.getInstance().getSummarySheetManager().removeProcedure(procedure);

            System.out.println(CatERing.getInstance().getSummarySheetManager().getCurrentSheet());
        } catch (UseCaseLogicException | SummarySheetException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
