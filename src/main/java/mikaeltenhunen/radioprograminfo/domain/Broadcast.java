package mikaeltenhunen.radioprograminfo.domain;

import java.time.ZonedDateTime;

public class Broadcast {
    private final ProgramName programName;
    private final String description;
    private final ZonedDateTime lastSent;

    public Broadcast(ProgramName programName, String description, ZonedDateTime lastSent) {
        this.programName = programName;
        this.description = description;
        this.lastSent = lastSent;
    }

    public ProgramName getProgramName() {
        return programName;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getLastSent() {
        return lastSent;
    }
}
