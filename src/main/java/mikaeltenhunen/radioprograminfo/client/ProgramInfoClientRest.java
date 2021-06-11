package mikaeltenhunen.radioprograminfo.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import mikaeltenhunen.radioprograminfo.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@CacheConfig(cacheNames = {"programNameToIdCache"})
public class ProgramInfoClientRest implements ProgramInfoClient {

    static final String ALL_PROGRAMS_PATH = "/api/v2/programs/index?format=JSON&pagination=false";
    static final String GET_LATEST_PATH = "/api/v2/episodes/getlatest?format=JSON";
    private final String baseUrl;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebClient webClient;

    public ProgramInfoClientRest(WebClient webClient, String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    @Override
    @Cacheable("programNameToIdCache")
    @Bulkhead(name = "srClient")
    public Mono<Map<ProgramName, ProgramId>> getAllPrograms() {
        logger.info("Retrieving all programs");
        return webClient.get()
                .uri(baseUrl + ALL_PROGRAMS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Programs.class)
                .map(programs -> programs.getPrograms().stream()
                        .collect(Collectors.toMap(Program::getName, Program::getId)))
                .doOnNext(nameToIdMap -> logger.debug("nameToIdMap={}", nameToIdMap));
    }

    @Override
    @Bulkhead(name = "srClient")
    public Mono<Episode> getLatestEpisode(ProgramId id) {
        return webClient.get()
                .uri(baseUrl + GET_LATEST_PATH + "&programid=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Episode.class);
    }
}
