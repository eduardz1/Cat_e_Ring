package main.businesslogic.shift;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

/**
 * Shift
 */
public class Shift {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private final Map<Cook, Integer> shiftMap;

    public Shift(LocalDate date, LocalTime startTime, LocalTime endTime, Map<Cook, Integer> shiftMap) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftMap = shiftMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Shift other = (Shift) obj;
        return this.date.equals(other.date) && this.startTime.equals(other.startTime)
                && this.endTime.equals(other.endTime);
    }
    
}