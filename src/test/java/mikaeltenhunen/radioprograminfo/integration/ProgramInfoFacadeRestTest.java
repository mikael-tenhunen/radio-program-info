package mikaeltenhunen.radioprograminfo.integration;

import com.github.benmanes.caffeine.cache.Caffeine;
import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Disabled
class ProgramInfoFacadeRestTest {

    ProgramInfoFacadeRest target;

    @BeforeEach
    public void setUp() {
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
        Caffeine caffeine = Caffeine.newBuilder()
                .initialCapacity(1)
                .maximumSize(1)
                .expireAfterWrite(24, TimeUnit.HOURS);
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        target = new ProgramInfoFacadeRest(webClient, cacheManager);
    }

    // TODO DO NOT CALL REAL API LIKE HERE
    @Test
    public void getAllPrograms() {
        Map<ProgramName, ProgramId> allPrograms = target.getAllPrograms();
        System.out.println(allPrograms);
        target.getAllPrograms();
        target.getAllPrograms();
    }

    // TODO DO NOT CALL REAL API LIKE HERE
    @Test
    public void getLastBroadcast() {
        target.getLastBroadcast(new ProgramId(5168));
    }

}