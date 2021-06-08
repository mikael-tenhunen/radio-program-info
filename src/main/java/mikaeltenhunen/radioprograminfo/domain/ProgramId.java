package mikaeltenhunen.radioprograminfo.domain;

import java.util.Objects;

public class ProgramId {
    private final String id;

    public ProgramId(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public ProgramId(int id) {
        this.id = String.valueOf(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramId programId = (ProgramId) o;
        return id.equals(programId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
