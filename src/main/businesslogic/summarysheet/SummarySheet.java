package main.businesslogic.summarysheet;

import java.security.Provider.Service;
import java.util.Map;

import javafx.collections.FXCollections;
import main.businesslogic.event.Event;

/**
 * SumarySheet
 */
public class SummarySheet {
    private static final Map<Integer, SummarySheet> loadedSheets = FXCollections.observableHashMap();
    private int id;

    protected SummarySheet(Service service) {
    }

    public SummarySheet(Service service, Event event) {
    }

}