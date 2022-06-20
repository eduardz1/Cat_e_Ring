package main.businesslogic.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.menu.Menu;
import main.persistence.PersistenceManager;

import java.sql.Date;
import java.sql.Time;

public class ServiceInfo implements EventItemInfo {
    private final String name;
    private int id;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private Menu menu;
    private int eventId;

    public ServiceInfo(String name) {
        this.name = name;
    }

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query =
                "SELECT id, name, service_date, time_start, time_end, expected_participants, approved_menu_id "
                        + "FROM Services WHERE event_id = "
                        + event_id
                        + ";";
        ObservableList<Menu> menus = CatERing.getInstance().getMenuManager().getAllMenus();
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    String s = rs.getString("name");
                    ServiceInfo serv = new ServiceInfo(s);
                    serv.id = rs.getInt("id");
                    serv.date = rs.getDate("service_date");
                    serv.timeStart = rs.getTime("time_start");
                    serv.timeEnd = rs.getTime("time_end");
                    serv.participants = rs.getInt("expected_participants");
                    serv.eventId = event_id;
                    int idMenu = rs.getInt("approved_menu_id");
                    for (Menu m : menus) {
                        if (m.getId() == idMenu) {
                            serv.menu = m;
                        }
                    }
                    result.add(serv);
                });
        return result;
    }

    public static ServiceInfo loadServiceInfo(int service_id) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query =
                "SELECT event_id, name, service_date, time_start, time_end, expected_participants, approved_menu_id "
                        + "FROM Services WHERE id = "
                        + service_id
                        + ";";
        ObservableList<Menu> menus = CatERing.getInstance().getMenuManager().getAllMenus();
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    String s = rs.getString("name");
                    ServiceInfo serv = new ServiceInfo(s);
                    serv.id = service_id;
                    serv.date = rs.getDate("service_date");
                    serv.timeStart = rs.getTime("time_start");
                    serv.timeEnd = rs.getTime("time_end");
                    serv.participants = rs.getInt("expected_participants");
                    serv.eventId = rs.getInt("event_id");
                    int idMenu = rs.getInt("approved_menu_id");
                    for (Menu m : menus) {
                        if (m.getId() == idMenu) {
                            serv.menu = m;
                        }
                    }
                    result.add(serv);
                });
        return result.get(0);
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public int getParticipants() {
        return participants;
    }

    public EventInfo getEventInfo() {
        return EventInfo.loadEventFromID(this.eventId);
    }

    public Menu getMenu() {
        return this.menu;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ServiceInfo other = (ServiceInfo) obj;
        return id == other.id;
    }

    public String toString() {
        return name
                + ": "
                + date
                + " ("
                + timeStart
                + "-"
                + timeEnd
                + "), "
                + participants
                + " pp.";
    }
}
