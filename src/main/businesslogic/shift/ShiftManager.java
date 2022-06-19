package main.businesslogic.shift;

import javafx.collections.ObservableList;
import main.businesslogic.UseCaseLogicException;
import main.businesslogic.user.User;

import java.time.Duration;
import java.util.ArrayList;

/** ShiftManager */
public class ShiftManager {

    private final ArrayList<ShiftManagerEventReceiver> eventReceivers;
    private ShiftBoard shiftBoard;

    public ShiftManager() {
        this.shiftBoard = ShiftBoard.getShiftBoard();
        this.eventReceivers = new ArrayList<>();
    }

    public void removeShift(User cook, Shift shift) throws UseCaseLogicException {
        if (!cook.isCook()) {
            throw new UseCaseLogicException("removeShift: " + "user specified is not a cook");
        }
        if (shift == null) {
            throw new IllegalArgumentException("removeShift: " + "shift can´t be null");
        }

        shiftBoard.removeShift(cook, shift);
    }

    public ObservableList<Shift> getShifts() {
        return ShiftBoard.getAllShifts();
    }

    public boolean isAssigned(User cook, Shift shift) throws UseCaseLogicException {
        if (shiftBoard == null) {
            throw new UseCaseLogicException("isAssigned: " + "shiftBoard was not initialized");
        }
        if (!cook.isCook()) {
            throw new UseCaseLogicException("isAssigned: " + "user specified is not a cook");
        }
        if (shift == null) {
            throw new IllegalArgumentException("isAssigned: " + "shift can´t be null");
        }

        return this.shiftBoard.isAssigned(cook, shift);
    }

    public boolean isAvailable(User cook, Shift shift, Duration time) throws UseCaseLogicException {
        if (shiftBoard == null) {
            throw new UseCaseLogicException("isAvailable: " + "shiftBoard was not initialized");
        }
        if (!cook.isCook()) {
            throw new UseCaseLogicException("isAvailable: " + "user specified is not a cook");
        }
        if (shift == null) {
            throw new IllegalArgumentException("isAvailable: " + "shift can´t be null");
        }
        return this.shiftBoard.isAvailable(cook, shift, time);
    }

    public void addEventReceiver(ShiftManagerEventReceiver rec) {
        this.eventReceivers.add(rec);
    }

    public void removeEventReceiver(ShiftManagerEventReceiver rec) {
        this.eventReceivers.remove(rec);
    }

    public ShiftBoard getShiftBoard() {
        return this.shiftBoard;
    }

    public void setShiftBoard(ShiftBoard shiftBoard) {
        if (shiftBoard == null) {
            throw new IllegalArgumentException("setShiftBoard: " + "shiftBoard can't be null");
        }

        this.shiftBoard = shiftBoard;
    }
}
