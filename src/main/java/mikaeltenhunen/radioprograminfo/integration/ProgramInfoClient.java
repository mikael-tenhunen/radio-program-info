package mikaeltenhunen.radioprograminfo.integration;

import mikaeltenhunen.radioprograminfo.domain.Episode;
import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ProgramInfoClient {
    Mono<Map<ProgramName, ProgramId>> getAllPrograms();
    Mono<Episode> getLatestEpisode(ProgramId id);
}
