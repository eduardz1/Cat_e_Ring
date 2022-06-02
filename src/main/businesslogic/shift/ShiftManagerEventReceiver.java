package main.businesslogic.shift;

/**
 * ShiftManagerEventReceiver
 */
public interface ShiftManagerEventReceiver {

    void updateShiftRemoved(Cook cook, Shift shift);

}