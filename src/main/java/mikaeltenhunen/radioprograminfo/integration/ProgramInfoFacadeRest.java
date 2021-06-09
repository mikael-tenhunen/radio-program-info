package mikaeltenhunen.radioprograminfo.integration;

import mikaeltenhunen.radioprograminfo.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.stream.Collectors;

public class ProgramInfoFacadeRest implements ProgramInfoFacade {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebClient webClient;

    public ProgramInfoFacadeRest(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Map<ProgramName, ProgramId> getAllPrograms() {
        return webClient.get()
                .uri("api.sr.se/api/v2/programs/index?format=JSON&pagination=false")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Programs.class)
                .map(programs -> programs.getPrograms().stream()
                        .collect(Collectors.toMap(Program::getName, Program::getId)))
                .doOnNext(nameToIdMap -> logger.debug("nameToIdMap={}", nameToIdMap))
                .block();
    }

    @Override
    public Episode getLastBroadcast(ProgramId id) {
        return webClient.get()
                .uri("api.sr.se/api/v2/episodes/getlatest?format=JSON&programid=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Episode.class)
                .log()
                .block();
    }
}
