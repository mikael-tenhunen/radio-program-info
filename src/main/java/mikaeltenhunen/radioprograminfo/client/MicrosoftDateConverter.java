package mikaeltenhunen.radioprograminfo.client;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class MicrosoftDateConverter {

    public static ZonedDateTime convert(String msDateAtUtc) {
        String timestamp = msDateAtUtc.replaceAll(".*/Date\\(([\\d+\\-]+)\\)/.*", "$1");
        Instant instant = Instant.ofEpochMilli(Long.parseLong(timestamp));
        return instant.atZone(ZoneOffset.UTC);
    }
}
