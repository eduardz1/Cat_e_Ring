package test.menu;

import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.menu.Menu;
import main.businesslogic.menu.MenuItem;
import main.businesslogic.menu.Section;
import main.businesslogic.procedure.Procedure;

public class TestCatERing4c {
    public static void main(String[] args) {
        try {
            /*
             * System.out.println("TEST DATABASE CONNECTION");
             * PersistenceManager.testSQLConnection();
             */
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            Menu m = CatERing.getInstance().getMenuManager().createMenu("Menu Pinco Pallino");
            Section antipasti = CatERing.getInstance().getMenuManager().defineSection("Antipasti");
            Section primi = CatERing.getInstance().getMenuManager().defineSection("Primi");
            Section secondi = CatERing.getInstance().getMenuManager().defineSection("Secondi");

            ObservableList<Procedure> recipes =
                    CatERing.getInstance().getProcedureManager().getRecipes();
            MenuItem it1 =
                    CatERing.getInstance().getMenuManager().insertItem(recipes.get(0), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(1), antipasti);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(2), antipasti);
            MenuItem it2 =
                    CatERing.getInstance().getMenuManager().insertItem(recipes.get(6), secondi);
            MenuItem it3 =
                    CatERing.getInstance().getMenuManager().insertItem(recipes.get(7), secondi);
            CatERing.getInstance().getMenuManager().insertItem(recipes.get(3));
            MenuItem freeit = CatERing.getInstance().getMenuManager().insertItem(recipes.get(4));
            System.out.println(m.testString());

            System.out.println("\nTEST REMOVE ITEM");
            CatERing.getInstance().getMenuManager().deleteItem(it1);
            CatERing.getInstance().getMenuManager().deleteItem(it2);
            CatERing.getInstance().getMenuManager().deleteItem(freeit);
            System.out.println(m.testString());

        } catch (UseCaseLogicException ex) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
