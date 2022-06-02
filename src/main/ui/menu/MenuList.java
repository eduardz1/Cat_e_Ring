package main.ui.menu;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import main.businesslogic.CatERing;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.menu.Menu;
import main.businesslogic.menu.MenuException;
import main.businesslogic.user.User;

import java.util.Optional;

public class MenuList {
    private MenuManagement menuManagementController;

    @FXML
    ListView<Menu> menuList;

    @FXML
    Button eliminaButton;
    @FXML
    Button apriButton;
    @FXML
    Button copiaButton;

    ObservableList<Menu> menuListItems;

    @FXML
    public void nuovoButtonPressed() {
        TextInputDialog dial = new TextInputDialog("Menu");
        dial.setTitle("Crea un nuovo menu");
        dial.setHeaderText("Scegli il titolo per il nuovo menu");
        Optional<String> result = dial.showAndWait();

        if (result.isPresent()) {
            try {
                Menu m = CatERing.getInstance().getMenuManager().createMenu(result.get());
                menuListItems.add(m);
                menuManagementController.showCurrentMenu();
            } catch (UseCaseLogicException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void fineButtonPressed() {
        menuManagementController.endMenuManagement();
    }

    public void setParent(MenuManagement menuManagement) {
        menuManagementController = menuManagement;
    }

    public void initialize() {
        if (menuListItems == null) {
            menuListItems = CatERing.getInstance().getMenuManager().getAllMenus();
            menuList.setItems(menuListItems);
            menuList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            menuList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldMenu, newMenu) -> {
                User u = CatERing.getInstance().getUserManager().getCurrentUser();
                eliminaButton.setDisable(newMenu == null || newMenu.isInUse() || !newMenu.isOwner(u));
                apriButton.setDisable(newMenu == null || newMenu.isInUse() || !newMenu.isOwner(u));
                copiaButton.setDisable(newMenu == null);
            });
        } else {
            menuList.refresh();
        }
    }

    @FXML
    public void eliminaButtonPressed() {
        try {
            Menu m = menuList.getSelectionModel().getSelectedItem();
            CatERing.getInstance().getMenuManager().deleteMenu(m);
            menuListItems.remove(m);
        } catch (UseCaseLogicException | MenuException ex) {
            ex.printStackTrace();
        }
    }

    @FXML void apriMenuButtonPressed() {
        try {
            Menu m = menuList.getSelectionModel().getSelectedItem();
            CatERing.getInstance().getMenuManager().chooseMenu(m);
            menuManagementController.showCurrentMenu();
        } catch (UseCaseLogicException | MenuException ex) {
            ex.printStackTrace();
        }
    }

    @FXML void copiaMenuButtonPressed() {
        try {
            Menu m = menuList.getSelectionModel().getSelectedItem();
            Menu copia = CatERing.getInstance().getMenuManager().copyMenu(m);
            menuListItems.add(copia);
            menuManagementController.showCurrentMenu();
        } catch (UseCaseLogicException ex) {
            ex.printStackTrace();
        }
    }

    public void selectMenu(Menu m) {
        if (m != null) this.menuList.getSelectionModel().select(m);
        else this.menuList.getSelectionModel().select(-1);
    }
}
