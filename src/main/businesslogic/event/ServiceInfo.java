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
    private Menu menu;
    private EventInfo eventInfo;

    public ServiceInfo(String name) {
        this.name = name;
    }

    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id, EventInfo e) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants, approved_menu_id " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, rs -> {
            String s = rs.getString("name");
            ServiceInfo serv = new ServiceInfo(s);
            serv.id = rs.getInt("id");
            serv.date = rs.getDate("service_date");
            serv.timeStart = rs.getTime("time_start");
            serv.timeEnd = rs.getTime("time_end");
            serv.participants = rs.getInt("expected_participants");
            int idMenu=rs.getInt("approved_menu_id");
            ObservableList<Menu> Omenu = CatERing.getInstance().getMenuManager().getAllMenus();
            for(int i = 0; i < Omenu.size(); i++) {
                if(Omenu.get(i).getId()==idMenu) serv.menu = Omenu.get(i);
            }
            serv.eventInfo = e; // temporary, need to change when working on Event/Services UC
            result.add(serv);
        });

        return result;
    }
}
