package Controllers;

import java.time.ZoneId;

public class BaseAppointmentControl {
    protected final static String pattern = "HH:mm";
    protected final static ZoneId businessZone = ZoneId.of("America/New_York");
    protected final static String businessStartTime = "08:00";
    protected final static String businessEndTime = "22:00";
}
