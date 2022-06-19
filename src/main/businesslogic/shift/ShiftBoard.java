package main.businesslogic.shift;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.user.User;

import java.time.Duration;

/** ShiftBoard */
public class ShiftBoard {
    private static volatile ShiftBoard instance;
    private final ObservableSet<Shift> shiftList = FXCollections.observableSet();

    private ShiftBoard() {
        shiftList.addAll(Shift.getAllShifts());
    }

    public static ObservableList<Shift> getAllShifts() {
        return Shift.getAllShifts();
    }

    public static ShiftBoard getShiftBoard() {
        if (instance == null) {
            synchronized (ShiftBoard.class) {
                if (instance == null) {
                    instance = new ShiftBoard();
                }
            }
        }
        return instance;
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
        StringBuilder builder = new StringBuilder();
        builder.append("TABELLONE DEI TURNI\n");
        this.shiftList.forEach(
                shift -> {
                    builder.append("\t");
                    builder.append(shift.toString());
                    builder.append("\n");
                });
        return builder.toString();
    }
}
