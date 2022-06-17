package main.businesslogic.shift;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.user.User;

import java.time.Duration;
import java.time.LocalDate;

/** Shift */
public class Shift {
    private final ObservableMap<User, Duration> myCooks;
    private LocalDate date;
    private Duration startTime;
    private Duration endTime;
    private int id;

    public Shift(User user, LocalDate date, Duration startTime, Duration endTime) {
        this.id = 0;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.myCooks = FXCollections.observableHashMap();
        myCooks.put(user, endTime.minus(startTime));
    }

    public void setStartTime(Duration startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Duration endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
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
        myCooks.put(cook, myCooks.get(cook).plus(time));
    }

    public void decreaseAvailableTime(User cook, Duration time) throws UseCaseLogicException {
        if (!isAvailable(cook, time)) {
            throw new UseCaseLogicException(
                    "decreaseAvailableTime: " + "cook does not have enough time left");
        }

        myCooks.put(cook, myCooks.get(cook).minus(time));
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
