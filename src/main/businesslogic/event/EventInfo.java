package main.businesslogic.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.user.User;
import main.persistence.PersistenceManager;

import java.sql.Date;

public class EventInfo implements EventItemInfo {
    private final String name;
    private int id;
    private Date dateStart;
    private Date dateEnd;
    private int participants;
    private User organizer;
    private ObservableList<ServiceInfo> services;

    public EventInfo(String name) {
        this.name = name;
        id = 0;
    }

    public static ObservableList<EventInfo> loadAllEventInfo() {
        ObservableList<EventInfo> all = FXCollections.observableArrayList();
        String query = "SELECT * FROM Events WHERE true";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    String n = rs.getString("name");
                    EventInfo e = new EventInfo(n);
                    e.id = rs.getInt("id");
                    e.dateStart = rs.getDate("date_start");
                    e.dateEnd = rs.getDate("date_end");
                    e.participants = rs.getInt("expected_participants");
                    int org = rs.getInt("organizer_id");
                    e.organizer = User.loadUserById(org);
                    all.add(e);
                });

        for (EventInfo e : all) {
            e.services = ServiceInfo.loadServiceInfoForEvent(e.id, e);
        }
        return all;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public int getParticipants() {
        return participants;
    }

    public User getOrganizer() {
        return organizer;
    }

    public ObservableList<ServiceInfo> getServices() {
        return FXCollections.unmodifiableObservableList(this.services);
    }

    // STATIC METHODS FOR PERSISTENCE

    public String toString() {
        return name
                + ": "
                + dateStart
                + "-"
                + dateEnd
                + ", "
                + participants
                + " pp. ("
                + organizer.getUserName()
                + ")";
    }
}
