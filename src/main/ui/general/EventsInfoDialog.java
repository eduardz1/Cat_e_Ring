package main.ui.general;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import main.businesslogic.CatERing;
import main.businesslogic.event.EventInfo;
import main.businesslogic.event.EventItemInfo;
import main.businesslogic.event.ServiceInfo;

public class EventsInfoDialog {

    private Stage myStage;

    @FXML
    TreeView<EventItemInfo> eventTree;

    public void initialize() {
        ObservableList<EventInfo> all = CatERing.getInstance().getEventManager().getEventInfo();
        eventTree.setShowRoot(false);
        TreeItem<EventItemInfo> root = new TreeItem<>(null);

        for (EventInfo e: all) {
            TreeItem<EventItemInfo> node = new TreeItem<>(e);
            root.getChildren().add(node);
            ObservableList<ServiceInfo> serv = e.getServices();
            for (ServiceInfo s: serv) {
                node.getChildren().add(new TreeItem<>(s));
            }
        }

        eventTree.setRoot(root);
    }

    @FXML
    public void okButtonPressed() {
        myStage.close();
    }

    public void setOwnStage(Stage stage) {
        myStage = stage;
    }
}
