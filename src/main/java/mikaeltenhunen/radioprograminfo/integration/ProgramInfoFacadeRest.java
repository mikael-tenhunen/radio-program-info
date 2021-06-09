package mikaeltenhunen.radioprograminfo.integration;

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
public class ProgramInfoFacadeRest implements ProgramInfoFacade {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebClient webClient;

    public ProgramInfoFacadeRest(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    @Cacheable("programNameToIdCache")
    @Bulkhead(name = "srClient")
    public Mono<Map<ProgramName, ProgramId>> getAllPrograms() {
        logger.info("In getAllPrograms...");
        return webClient.get()
                .uri("api.sr.se/api/v2/programs/index?format=JSON&pagination=false")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Programs.class)
                .map(programs -> programs.getPrograms().stream()
                        .collect(Collectors.toMap(Program::getName, Program::getId)))
                .doOnNext(nameToIdMap -> logger.debug("nameToIdMap={}", nameToIdMap));
    }

    @Override
    @Bulkhead(name = "srClient")
    public Mono<Episode> getLastBroadcast(ProgramId id) {
        return webClient.get()
                .uri("api.sr.se/api/v2/episodes/getlatest?format=JSON&programid=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Episode.class)
                .log();
    }
}
