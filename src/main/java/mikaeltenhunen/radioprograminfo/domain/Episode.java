package mikaeltenhunen.radioprograminfo.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import mikaeltenhunen.radioprograminfo.client.EpisodeDeserializer;

import java.time.ZonedDateTime;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Episode episode = (Episode) o;
        return title.equals(episode.title) && description.equals(episode.description) && publishDateUtc.equals(episode.publishDateUtc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, publishDateUtc);
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
