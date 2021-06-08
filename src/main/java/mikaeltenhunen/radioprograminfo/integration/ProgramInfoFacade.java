package mikaeltenhunen.radioprograminfo.integration;

import mikaeltenhunen.radioprograminfo.domain.Episode;
import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;

import java.util.Map;

public interface ProgramInfoFacade {
    Map<ProgramName, ProgramId> getAllPrograms();
    Episode getLastBroadcast(ProgramId id);
}
