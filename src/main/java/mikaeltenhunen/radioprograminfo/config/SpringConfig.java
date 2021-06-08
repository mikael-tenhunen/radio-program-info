package mikaeltenhunen.radioprograminfo.config;

import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacade;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacadeRest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpringConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public ProgramInfoFacade programInfoFacade(WebClient webClient) {
        return new ProgramInfoFacadeRest(webClient);
    }
}
