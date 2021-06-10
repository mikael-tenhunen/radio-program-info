package mikaeltenhunen.radioprograminfo.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import mikaeltenhunen.radioprograminfo.domain.Episode;

import java.io.IOException;
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
        JsonNode episode = node.get("episode");
        String title = episode.get("title").asText();
        String description = episode.get("description").asText();
        String microsoftDate = episode.get("publishdateutc").asText();

        ZonedDateTime publishDateUtc = MicrosoftDateConverter.convert(microsoftDate);
        return new Episode(title, description, publishDateUtc);
    }


}
