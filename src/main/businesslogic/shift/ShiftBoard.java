package main.businesslogic.shift;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.event.EventInfo;
import main.businesslogic.user.User;
import main.persistence.PersistenceManager;
import main.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/** ShiftBoard */
public class ShiftBoard {
    private final Set<Shift> shiftList; // FIXME need to pass a key

    public ShiftBoard() {
        this.shiftList = new HashSet<>();
    }

    public void removeShift(User cook, Shift shift) {}

    public boolean isAssigned(User cook, Shift shift) throws UseCaseLogicException {
        if (!this.shiftList.contains(shift)) {
            throw new UseCaseLogicException(
                    "isAssigned: " + "shift is not present in the shift board");
        }

        return shift.isAssigned(cook);
    }

    public boolean isAvailable(User cook, Shift shift, Duration time) throws UseCaseLogicException {
        if (!this.shiftList.contains(shift)) {
            throw new UseCaseLogicException(
                    "isAssigned: " + "shift is not present in the shift board");
        }

        return shift.isAvailable(cook, time);
    }

    public static ObservableList<Shift> getAllShifts() {
        return Shift.getAllShifts();
    }

    @Override
    public String toString() {
        return "ShiftBoard{" +
                "shiftList=" + shiftList +
                '}';
    }
}
