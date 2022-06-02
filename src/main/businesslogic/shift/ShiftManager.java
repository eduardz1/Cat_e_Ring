package main.businesslogic.shift;

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

    public boolean isAvailable(Cook cook, Shift shift) {
        return false; // TODO implement
    }

    public void removeShift(Cook cook, Shift shift) {
        shiftBoard.removeShift(cook, shift); // TODO implement, check exception
    }

    public ArrayList<Shift> getShifts() {
        return null;
    }

    private void notifyShiftRemoved(Cook cook, Shift shift) {
        for (ShiftManagerEventReceiver eventReceiver : eventReceiver) {
            eventReceiver.updateShiftRemoved(cook, shift);
        }
    }
}