package mikaeltenhunen.radioprograminfo.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MicrosoftDateConverterTest {

    @Test
    public void utcDateStampConvertedCorrectly() {
        ZonedDateTime zonedDateTime = MicrosoftDateConverter.convert("/Date(1522818000000)/");
        assertEquals(ZonedDateTime.parse("2018-04-04T05:00:00Z[UTC]"), zonedDateTime);
    }

    @Test
    public void throwsException_WhenGivenBadInput() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> MicrosoftDateConverter.convert("2018-04-04T05:00:00Z[UTC]"));
    }
}