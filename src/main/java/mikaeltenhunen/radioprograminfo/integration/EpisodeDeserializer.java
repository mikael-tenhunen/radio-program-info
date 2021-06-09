package mikaeltenhunen.radioprograminfo.integration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
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
        TreeNode node = jsonParser.getCodec().readTree(jsonParser).get("episode");
        String title = node.get("title").toString();
        String description = node.get("description").toString();
        String microsoftDate = node.get("publishdateutc").toString();
        ZonedDateTime publishDateUtc = MicrosoftDateConverter.convert(microsoftDate);
        return new Episode(title, description, publishDateUtc);
    }


}
