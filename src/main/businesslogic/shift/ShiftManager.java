package main.businesslogic.shift;

import main.businesslogic.UseCaseLogicException;
import main.businesslogic.user.User;

import java.time.Duration;
import java.util.ArrayList;

/**
 * ShiftManager
 */
public class ShiftManager {

    private ShiftBoard shiftBoard;
    private final ArrayList<ShiftManagerEventReceiver> eventReceiver;

    public ShiftManager() {
        this.eventReceiver = new ArrayList<>();
    }

    public void removeShift(User cook, Shift shift) throws UseCaseLogicException {
        if (!cook.isCook()) {
            throw new UseCaseLogicException("removeShift: " + "user specified is not a cook");
        }
        if (cook == null) {
            throw new IllegalArgumentException("removeShift: " + "user can´t be null");
        }
        if (shift == null) {
            throw new IllegalArgumentException("removeShift: " + "shift can´t be null");
        }

        shiftBoard.removeShift(cook, shift); //TODO implement, check exception
    }

    public ArrayList<Shift> getShifts() {
        return null;
    }

    // FIXME wtf what are you
    private void notifyShiftRemoved(User cook, Shift shift) {
        if (cook == null) {
            throw new IllegalArgumentException("notifyShiftRemoved: " + "user can´t be null");
        }
        if (shift == null) {
            throw new IllegalArgumentException("notifyShiftRemoved: " + "shift can´t be null");
        }

        for (ShiftManagerEventReceiver eventReceiver : eventReceiver) {
            eventReceiver.updateShiftRemoved(cook, shift);
        }
    }

    public void setShiftBoard(ShiftBoard shiftBoard) {
        if (shiftBoard == null) {
            throw new IllegalArgumentException("setShiftBoard: " + "shiftBoard can't be null");
        }

        this.shiftBoard = shiftBoard;
    }

    public boolean isAssigned(User cook, Shift shift) throws UseCaseLogicException {
        if (shiftBoard == null) {
            throw new UseCaseLogicException("isAssigned: " + "shiftBoard was not initialized");
        }
        if (!cook.isCook()) {
            throw new UseCaseLogicException("isAssigned: " + "user specified is not a cook");
        }
        if (cook == null) {
            throw new IllegalArgumentException("isAssigned: " + "user can´t be null");
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
            throw new UseCaseLogicException("isAvailable: " + "user specified is not a cook");}
        if (cook == null) {
            throw new IllegalArgumentException("isAvailable: " + "user can´t be null");
        }
        if (shift == null) {
            throw new IllegalArgumentException("isAvailable: " + "shift can´t be null");
        }
        return this.shiftBoard.isAvailable(cook, shift, time);
    }

}