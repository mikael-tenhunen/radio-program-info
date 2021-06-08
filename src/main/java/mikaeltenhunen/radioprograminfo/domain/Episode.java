package mikaeltenhunen.radioprograminfo.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import mikaeltenhunen.radioprograminfo.integration.EpisodeDeserializer;

import java.time.ZonedDateTime;

@JsonDeserialize(using = EpisodeDeserializer.class)
public class Episode {
    private String title;
    private String description;
    private ZonedDateTime publishDateUtc;

    public Episode() {
    }

    public Episode(String title, String description, ZonedDateTime publishDateUtc) {
        this.title = title;
        this.description = description;
        this.publishDateUtc = publishDateUtc;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getPublishDateUtc() {
        return publishDateUtc;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishDateUtc=" + publishDateUtc +
                '}';
    }
}
