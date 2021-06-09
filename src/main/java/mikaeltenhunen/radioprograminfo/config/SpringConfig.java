package mikaeltenhunen.radioprograminfo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacade;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacadeRest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@Configuration
//@EnableCaching
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

//    @Bean
//    public Caffeine programNameToIdCache() {
//        return Caffeine.newBuilder()
//                .initialCapacity(1)
//                .maximumSize(1)
//                .expireAfterWrite(24, TimeUnit.HOURS)
//                .recordStats();
//    }
//
//    @Bean
//    public CacheManager cacheManager(Caffeine programNameToIdCache) {
//        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
//        caffeineCacheManager.setCaffeine(programNameToIdCache);
//        return caffeineCacheManager;
//    }

//    @Bean
//    public ProgramInfoFacade programInfoFacade(WebClient webClient, CacheManager cacheManager) {
//        return new ProgramInfoFacadeRest(webClient, cacheManager);
//    }

    @Bean
    public ProgramInfoFacade programInfoFacade(WebClient webClient) {
        return new ProgramInfoFacadeRest(webClient, null);
    }


}
