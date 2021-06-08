package mikaeltenhunen.radioprograminfo.integration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import mikaeltenhunen.radioprograminfo.domain.Episode;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EpisodeDeserializer extends StdDeserializer<Episode> {

    public EpisodeDeserializer() {
        this(null);
    }

    protected EpisodeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Episode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String title = node.get("title").asText();
        String description = node.get("description").asText();
        String microsoftDate = node.get("publishdateutc").asText();
        System.out.println("MIIKKA " + microsoftDate);
        String timestamp = microsoftDate.replaceAll(".*/Date\\(([\\d+\\-]+)\\)/.*", "$1");
        Instant instant = Instant.ofEpochMilli(Long.parseLong(timestamp));
        ZonedDateTime publishDateUtc = instant.atZone(ZoneId.of("UTC"));

        return new Episode(title, description, publishDateUtc);
    }
}
