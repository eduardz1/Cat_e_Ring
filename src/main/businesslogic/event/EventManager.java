package main.businesslogic.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.procedure.Recipe;

public class EventManager {

    public EventManager()
    {

    }
    public ObservableList<EventInfo> getEventInfo() {
        return FXCollections.unmodifiableObservableList(EventInfo.loadAllEventInfo());
    }
}
