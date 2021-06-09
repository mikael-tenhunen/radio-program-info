package mikaeltenhunen.radioprograminfo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacade;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacadeRest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class SpringConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("programNameToIdCache");
        cacheManager.setCaffeine(caffeineCache());
        return cacheManager;
    }

    Caffeine<Object, Object > caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .recordStats();
    }

    @Bean
    public ProgramInfoFacade programInfoFacade(WebClient webClient) {
        return new ProgramInfoFacadeRest(webClient);
    }


}
