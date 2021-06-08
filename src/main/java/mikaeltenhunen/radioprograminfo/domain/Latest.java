package mikaeltenhunen.radioprograminfo.domain;

public class Latest {
    private Episode episode;

    public Latest() {
    }

    public Latest(Episode episode) {
        this.episode = episode;
    }

    public Episode getEpisode() {
        return episode;
    }

    @Override
    public String toString() {
        return "Latest{" +
                "episode=" + episode +
                '}';
    }
}
