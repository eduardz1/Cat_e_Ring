package main.businesslogic.shift;

import javafx.collections.ObservableList;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.user.User;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/** ShiftBoard */
public class ShiftBoard {
    private final Set<Shift> shiftList; // FIXME need to pass a key

    public ShiftBoard() {
        this.shiftList = new HashSet<>();
    }

    public static ObservableList<Shift> getAllShifts() {
        return Shift.getAllShifts();
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

    @Override
    public String toString() {
        return "ShiftBoard{" + "shiftList=" + shiftList + '}';
    }
}
