package main.businesslogic.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.CatERing;
import main.businesslogic.menu.Menu;
import main.persistence.PersistenceManager;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class ServiceInfo implements EventItemInfo {
    private final String name;
    private int id;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private Menu menu;
    private EventInfo eventInfo;
    public ServiceInfo(String name) {
        this.name = name;
    }

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id, EventInfo e) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants, approved_menu_id " +
                "FROM Services WHERE event_id = " + event_id;
            ObservableList <Menu> menus = CatERing.getInstance().getMenuManager().getAllMenus();
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
                    int idMenu = rs.getInt("approved_menu_id");
                    System.out.println(event_id + " id evento");
                    for (Menu m : menus) {
                        if (m.getId() == idMenu) {
                            serv.menu = m;
                            System.out.println(serv.menu.getId() + " id menu");
                        }
                    }
                    serv.eventInfo = e; // temporary, need to change when working on Event/Services UC
                    result.add(serv);
                });
        return result;
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
        return this.eventInfo;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public int getId() {
        return id;
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
