package main.businesslogic.shift;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.user.User;

import java.time.LocalDate;
import java.time.Duration;

/**
 * Shift
 */

public class Shift {
    public LocalDate getDate() {
        return date;
    }

    private LocalDate date;
    private Duration startTime;
    private Duration endTime;
    private final ObservableMap<User, Duration> myCooks;

    public Shift(LocalDate date, Duration startTime, Duration endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.myCooks = FXCollections.observableHashMap();
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
        return this.date.equals(other.date) && this.startTime.equals(other.startTime)
                && this.endTime.equals(other.endTime);
    }

    public ObservableList<User> getMyCooks(){
        return FXCollections.observableArrayList(myCooks.keySet());
    }

    public void increaseAvailableTime(User cook, Duration time) throws UseCaseLogicException{
        if (cook == null || time == null) {
            throw new IllegalArgumentException();
        }
        if(!isAssigned(cook)) {
            throw new UseCaseLogicException("increaseAvailableTime: " + "cook specified is not assigned to this Shift");
        }

        myCooks.put(cook, myCooks.get(cook).plus(time));
    }

    public void decreaseAvailableTime(User cook, Duration time) throws UseCaseLogicException{
        if(!isAvailable(cook, time)) {
            throw new UseCaseLogicException("decreaseAvailableTime: " + "cook does not have enough time left");
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
            throw new UseCaseLogicException("increaseAvailableTime: " + "cook specified is not assigned to this Shift");
        }
        return !myCooks.get(cook).minus(time).isZero();
    }
}