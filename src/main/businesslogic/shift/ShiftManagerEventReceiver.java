package main.businesslogic.shift;

import main.businesslogic.user.User;

import java.time.Duration;

/** ShiftManagerEventReceiver */
public interface ShiftManagerEventReceiver {

    void updateIncreasedTime(User cook, Shift shift, Duration time);
    void updateDecreasedTime (User cook, Shift shift, Duration time);
}
