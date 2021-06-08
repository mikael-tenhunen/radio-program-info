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
    public Broadcast getLastBroadcast(ProgramId id) {
        // TODO
        return null;
    }
}
