package mikaeltenhunen.radioprograminfo.config;

import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacade;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacadeRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

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
    public ProgramInfoFacade programInfoFacade(WebClient webClient) {
        return new ProgramInfoFacadeRest(webClient);
    }
}
