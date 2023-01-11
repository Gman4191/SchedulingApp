package DAO;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DBBase {
    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static ZoneId dbTimeZone = ZoneId.of("UTC");
}
