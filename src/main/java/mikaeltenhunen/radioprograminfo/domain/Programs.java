package mikaeltenhunen.radioprograminfo.domain;

import mikaeltenhunen.radioprograminfo.domain.Program;

import java.util.List;

public class Programs {
    private List<Program> programs;

    public Programs() {
    }

    public Programs(List<Program> programs) {
        this.programs = programs;
    }

    public List<Program> getPrograms() {
        return programs;
    }


}
