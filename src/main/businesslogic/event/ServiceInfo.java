package main.businesslogic.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.persistence.PersistenceManager;

import java.sql.Date;
import java.sql.Time;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private final String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;

    public ServiceInfo(String name) {
        this.name = name;
    }


    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, rs -> {
            String s = rs.getString("name");
            ServiceInfo serv = new ServiceInfo(s);
            serv.id = rs.getInt("id");
            serv.date = rs.getDate("service_date");
            serv.timeStart = rs.getTime("time_start");
            serv.timeEnd = rs.getTime("time_end");
            serv.participants = rs.getInt("expected_participants");
            result.add(serv);
        });

        return result;
    }
}