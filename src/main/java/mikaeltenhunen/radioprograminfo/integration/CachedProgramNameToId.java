package mikaeltenhunen.radioprograminfo.integration;

import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import mikaeltenhunen.radioprograminfo.domain.ProgramNameToId;

import java.util.Map;

public class CachedProgramNameToId implements ProgramNameToId {

    private final Map<ProgramName, ProgramId> nameToId;

    public CachedProgramNameToId(Map<ProgramName, ProgramId> nameToId) {
        this.nameToId = nameToId;
    }

    @Override
    public ProgramId getId(ProgramName name) {
        // TODO
        return null;
    }
}
