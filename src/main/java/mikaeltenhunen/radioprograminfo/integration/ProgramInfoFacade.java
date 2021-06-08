package mikaeltenhunen.radioprograminfo.integration;

import mikaeltenhunen.radioprograminfo.domain.Broadcast;
import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import mikaeltenhunen.radioprograminfo.domain.ProgramNameToId;

import java.util.Map;

public interface ProgramInfoFacade {
    Map<ProgramName, ProgramId> getAllPrograms();
    Broadcast getLastBroadcast(ProgramId id);
}
