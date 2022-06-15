package main.businesslogic.shift;

import main.businesslogic.user.User;

/**
 * ShiftManagerEventReceiver
 */
public interface ShiftManagerEventReceiver {

    void updateShiftRemoved(User cook, Shift shift);

}