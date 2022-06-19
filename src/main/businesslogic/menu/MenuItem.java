package main.businesslogic.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.procedure.Procedure;
import main.persistence.BatchUpdateHandler;
import main.persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private int id;
    private String description;
    private Procedure itemProcedure;

    private MenuItem() {}

    public MenuItem(Procedure rec) {
        this(rec, rec.getName());
    }

    public MenuItem(Procedure rec, String desc) {
        id = 0;
        itemProcedure = rec;
        description = desc;
    }

    public MenuItem(MenuItem mi) {
        this.id = 0;
        this.description = mi.description;
        this.itemProcedure = mi.itemProcedure;
    }

    public static void saveAllNewItems(int menuid, int sectionid, List<MenuItem> items) {
        String itemInsert =
                "INSERT INTO catering.MenuItems (menu_id, section_id, description, procedure_id, position) VALUES (?, ?, ?, ?, ?);";
        PersistenceManager.executeBatchUpdate(
                itemInsert,
                items.size(),
                new BatchUpdateHandler() {
                    @Override
                    public void handleBatchItem(PreparedStatement ps, int batchCount)
                            throws SQLException {
                        ps.setInt(1, menuid);
                        ps.setInt(2, sectionid);
                        ps.setString(
                                3,
                                PersistenceManager.escapeString(items.get(batchCount).description));
                        ps.setInt(4, items.get(batchCount).itemProcedure.getId());
                        ps.setInt(5, batchCount);
                    }

                    @Override
                    public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                        items.get(count).id = rs.getInt(1);
                    }
                });
    }

    public static void saveNewItem(int menuid, int sectionid, MenuItem mi, int pos) {
        String itemInsert =
                "INSERT INTO catering.MenuItems (menu_id, section_id, description, procedure_id, position) VALUES ("
                        + menuid
                        + ", "
                        + sectionid
                        + ", "
                        + "'"
                        + PersistenceManager.escapeString(mi.description)
                        + "', "
                        + mi.itemProcedure.getId()
                        + ", "
                        + pos
                        + ");";
        PersistenceManager.executeUpdate(itemInsert);

        mi.id = PersistenceManager.getLastId();
    }

    public static ObservableList<MenuItem> loadItemsFor(int menu_id, int sec_id) {
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        ArrayList<Integer> recids = new ArrayList<>();
        String query =
                "SELECT * FROM MenuItems WHERE menu_id = "
                        + menu_id
                        + " AND "
                        + "section_id = "
                        + sec_id
                        + " ORDER BY position";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    MenuItem mi = new MenuItem();
                    mi.description = rs.getString("description");
                    mi.id = rs.getInt("id");
                    result.add(mi);
                    recids.add(rs.getInt("procedure_id"));
                });

        // carico qui le ricette perché non posso innestare due connessioni al DB
        for (int i = 0; i < result.size(); i++) {
            result.get(i).itemProcedure = Procedure.loadProcedureById(recids.get(i));
        }

        return result;
    }

    public static void saveSection(int sec_id, MenuItem mi) {
        String upd = "UPDATE MenuItems SET section_id = " + sec_id + " WHERE id = " + mi.id;
        PersistenceManager.executeUpdate(upd);
    }

    public static void saveDescription(MenuItem mi) {
        String upd =
                "UPDATE MenuItems SET description = '"
                        + PersistenceManager.escapeString(mi.getDescription())
                        + "' WHERE id = "
                        + mi.id;
        PersistenceManager.executeUpdate(upd);
    }

    public static void removeItem(MenuItem mi) {
        String rem = "DELETE FROM MenuItems WHERE id = " + mi.getId();
        PersistenceManager.executeUpdate(rem);
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Procedure getItemProcedure() {
        return itemProcedure;
    }

    public void setItemProcedure(Procedure itemProcedure) {
        this.itemProcedure = itemProcedure;
    }
}
