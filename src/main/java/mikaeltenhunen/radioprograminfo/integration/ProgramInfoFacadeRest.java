package mikaeltenhunen.radioprograminfo.integration;

import mikaeltenhunen.radioprograminfo.domain.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.stream.Collectors;

public class ProgramInfoFacadeRest implements ProgramInfoFacade {

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
                .log()
                .map(programs -> programs.getPrograms().stream()
                        .collect(Collectors.toMap(Program::getName, Program::getId)))
                .block();
    }

    @Override
    public Episode getLastBroadcast(ProgramId id) {
        return webClient.get()
                .uri("api.sr.se/api/v2/episodes/getlatest?format=JSON&programid=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Latest.class)
                .map(Latest::getEpisode)
                .log()
                .block();
    }
}
