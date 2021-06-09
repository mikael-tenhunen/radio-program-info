package mikaeltenhunen.radioprograminfo.integration;

import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import mikaeltenhunen.radioprograminfo.domain.ProgramNameToId;

import java.util.Map;

public class CachedProgramNameToId implements ProgramNameToId {

    private final ProgramInfoFacade infoFacade;
    private final Map<ProgramName, ProgramId> nameToId;

    public CachedProgramNameToId(ProgramInfoFacade infoFacade, Map<ProgramName, ProgramId> nameToId) {
        this.infoFacade = infoFacade;
        this.nameToId = nameToId;
    }

    @Override
    public ProgramId getId(ProgramName name) {
        // TODO
        return infoFacade.getAllPrograms().get(name);
    }
}
