package main.businesslogic.shift;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.user.User;
import main.persistence.PersistenceManager;
import main.persistence.ResultHandler;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/** Shift */
public class Shift {
    private final ObservableMap<User, Duration> myCooks;
    private LocalDate date;
    private Time startTime;
    private Time endTime;
    private int id;
    // private ObservableSet<ShiftUser> shiftUsers;

    public Shift(LocalDate date, Time startTime, Time endTime) {
        this.id = 0;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.myCooks = FXCollections.observableHashMap();
        // this.shiftUsers = FXCollections.observableSet();
    //    myCooks.put(user, endTime.minus(startTime));
    }

    public static ObservableList<Shift> getAllShifts() {
        ObservableList<Shift> all = FXCollections.observableArrayList();
        String query = "SELECT * FROM shifts WHERE true;";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Date date = rs.getDate("date");
                LocalDate localDate = Instant.ofEpochMilli(date.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
                Time startTime = rs.getTime("startTime");
                Time endTime = rs.getTime("endTime");
                int id = rs.getInt("id");
                Shift shift = new Shift(localDate, startTime, endTime);
                shift.id=id;
                String query2 = "SELECT * FROM usershift WHERE id_shift = " + id;
                PersistenceManager.executeQuery(query2, new ResultHandler() {
                    @Override
                    public void handle(ResultSet rs) throws SQLException {
                        int idCook = rs.getInt("id_cook");
                        User u = User.loadUserById(idCook);
                        Duration time =Duration.ofMinutes(rs.getLong("availableTime"));
                        shift.myCooks.put(u, time);
                    }
                });
                all.add(shift);
            }
        });

        return all;
    }

    public static void saveNewTime(Shift shift, User cook, Duration time) {
        String upd = "UPDATE usershift SET availableTime = " +time.toMinutes()+ " WHERE id_cook = " + cook.getId()+ " AND id_shift = " + shift.getId() + ";";
        PersistenceManager.executeUpdate(upd);

    }


    @Override
    public String toString() {
        return "Shift{" +
                "myCooks=" + myCooks +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id=" + id +
                '}';
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Shift other = (Shift) obj;
        return this.date.equals(other.date)
                && this.startTime.equals(other.startTime)
                && this.endTime.equals(other.endTime);
    }

    public ObservableList<User> getMyCooks() {
        return FXCollections.observableArrayList(myCooks.keySet());
    }

    public void increaseAvailableTime(User cook, Duration time) {
        Duration plus = myCooks.get(cook).plus(time);
        myCooks.put(cook, plus);
        saveNewTime(this, cook, plus);
    }

    public void decreaseAvailableTime(User cook, Duration time) throws UseCaseLogicException {
        if (!isAvailable(cook, time)) {
            throw new UseCaseLogicException(
                    "decreaseAvailableTime: " + "cook does not have enough time left");
        }

        Duration minus = myCooks.get(cook).minus(time);
        myCooks.put(cook, minus);

         saveNewTime(this, cook, minus);

    }

    public boolean isAssigned(User cook) {
        return myCooks.containsKey(cook);
    }

    public boolean isAvailable(User cook, Duration time) throws UseCaseLogicException {
        if (cook == null || time == null) {
            throw new IllegalArgumentException();
        }
        if (!isAssigned(cook)) {
            throw new UseCaseLogicException(
                    "increaseAvailableTime: " + "cook specified is not assigned to this Shift");
        }
        return !myCooks.get(cook).minus(time).isZero();
    }

}
