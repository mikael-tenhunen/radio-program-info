package mikaeltenhunen.radioprograminfo.integration;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class MicrosoftDateConverter {

    public static ZonedDateTime convert(String msDateAtUtc) {
        String timestamp = msDateAtUtc.replaceAll(".*/Date\\(([\\d+\\-]+)\\)/.*", "$1");
        Instant instant = Instant.ofEpochMilli(Long.parseLong(timestamp));
        return instant.atZone(ZoneId.of("UTC"));
    }
}
