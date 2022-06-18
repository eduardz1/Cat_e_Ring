package main.persistence;

import main.businesslogic.shift.Shift;
import main.businesslogic.shift.ShiftManagerEventReceiver;
import main.businesslogic.user.User;

import java.time.Duration;

public class ShiftPersistence implements ShiftManagerEventReceiver {

    @Override
    public void updateIncreasedTime(User cook, Shift shift, Duration time) {
        // Shift.saveNewIncreasedTime(shift, cook, time);
    }

    @Override
    public void updateDecreasedTime(User cook, Shift shift, Duration time) {
        // Shift.saveNewDecreasedTime(shift, cook, time);
    }
}
