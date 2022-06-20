package main.businesslogic.shift;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.user.User;
import main.persistence.PersistenceManager;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/** Shift */
public class Shift {
    private final ObservableMap<User, Duration> myCooks;
    private LocalDate date;
    private Time startTime;
    private Time endTime;
    private int id;

    public Shift(LocalDate date, Time startTime, Time endTime) {
        this.id = 0;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.myCooks = FXCollections.observableHashMap();
    }

    public static ObservableList<Shift> getAllShifts() {
        ObservableList<Shift> all = FXCollections.observableArrayList();
        String query = "SELECT * FROM Shifts WHERE true;";
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    Date date = rs.getDate("date");
                    LocalDate localDate =
                            Instant.ofEpochMilli(date.getTime())
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();
                    Time startTime = rs.getTime("startTime");
                    Time endTime = rs.getTime("endTime");
                    int id = rs.getInt("id");
                    Shift shift = new Shift(localDate, startTime, endTime);
                    shift.id = id;
                    String query2 = "SELECT * FROM UserShifts WHERE id_shift = " + id;
                    PersistenceManager.executeQuery(
                            query2,
                            rs1 -> {
                                int idCook = rs1.getInt("id_cook");
                                User u = User.loadUserById(idCook);
                                Duration time = Duration.ofMinutes(rs1.getLong("availableTime"));
                                shift.myCooks.put(u, time);
                            });
                    all.add(shift);
                });

        return all;
    }

    public static Shift loadShiftById(int int1) {
        String query = "SELECT * FROM Shifts WHERE id = " + int1 + ";";
        ObservableList<Shift> res = FXCollections.observableArrayList();
        PersistenceManager.executeQuery(
                query,
                rs -> {
                    Date date = rs.getDate("date");
                    LocalDate localDate =
                            Instant.ofEpochMilli(date.getTime())
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();
                    Time startTime = rs.getTime("startTime");
                    Time endTime = rs.getTime("endTime");
                    int id = rs.getInt("id");
                    Shift shift = new Shift(localDate, startTime, endTime);
                    shift.id = id;
                    String query2 = "SELECT * FROM UserShifts WHERE id_shift = " + id;
                    PersistenceManager.executeQuery(
                            query2,
                            rs1 -> {
                                int idCook = rs1.getInt("id_cook");
                                User u = User.loadUserById(idCook);
                                Duration time = Duration.ofMinutes(rs1.getLong("availableTime"));
                                shift.myCooks.put(u, time);
                            });
                    res.add(shift);
                });
        if (res.size() == 0) {
            return null;
        }
        return res.get(0);
    }

    public static void saveNewTime(Shift shift, User cook, Duration time) {
        String upd =
                "UPDATE UserShifts SET availableTime = "
                        + time.toMinutes()
                        + " WHERE id_cook = "
                        + cook.getId()
                        + " AND id_shift = "
                        + shift.getId()
                        + ";";
        PersistenceManager.executeUpdate(upd);
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Shift(id=")
                .append(this.id)
                .append(")[date=")
                .append(date)
                .append(", endTime=")
                .append(endTime)
                .append(", startTime=")
                .append(startTime);
        if (myCooks.isEmpty()) {
            builder.append(", no cooks assigned");
        } else {
            builder.append(", myCooks=[");
            this.myCooks.forEach(
                    (k, v) -> {
                        builder.append(k.getUserName());
                        builder.append("=");
                        builder.append(v);
                        builder.append(" ");
                    });
        }
        builder.append("]");
        return builder.toString();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                    "isAvailable: "
                            + "cook specified (id="
                            + cook.getId()
                            + ") is not assigned to this Shift (id="
                            + id
                            + ")");
        }
        return !myCooks.get(cook).minus(time).isNegative();
    }
}
