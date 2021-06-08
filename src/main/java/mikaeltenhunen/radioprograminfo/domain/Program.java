package mikaeltenhunen.radioprograminfo.domain;

import java.util.Objects;

public class Program {
    private ProgramId id;
    private ProgramName name;

    public Program() {
    }

    public Program(ProgramId id, ProgramName name) {
        this.id = id;
        this.name = name;
    }

    public ProgramId getId() {
        return id;
    }

    public ProgramName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return id.equals(program.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
