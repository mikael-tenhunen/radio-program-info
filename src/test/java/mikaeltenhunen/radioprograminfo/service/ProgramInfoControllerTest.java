package mikaeltenhunen.radioprograminfo.service;

import mikaeltenhunen.radioprograminfo.client.ProgramInfoClient;
import mikaeltenhunen.radioprograminfo.domain.Episode;
import mikaeltenhunen.radioprograminfo.domain.Program;
import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebFluxTest(ProgramInfoController.class)
class ProgramInfoControllerTest {
    public static final ProgramId PROGRAM_ID = new ProgramId(123);
    public static final ProgramName PROGRAM_NAME = new ProgramName("Namnet på ett program");
    public static final ZonedDateTime PUBLISH_DATE_TIME = ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);

    @Autowired
    private WebTestClient testClient;
    @MockBean
    private ProgramInfoClient mockProgramInfoClient;

    @Test
    public void getLatestEpisode_happyCase() {
        Map<ProgramName, ProgramId> nameToId = Stream.of(new Program(PROGRAM_ID, PROGRAM_NAME))
                .collect(Collectors.toMap(Program::getName, Program::getId));
        Mockito.when(mockProgramInfoClient.getAllPrograms()).thenReturn(Mono.just(nameToId));
        Episode expected = new Episode("Det senaste avsnittet", "Handlar om grejer", PUBLISH_DATE_TIME);
        Mockito.when(mockProgramInfoClient.getLatestEpisode(PROGRAM_ID)).thenReturn(Mono.just(expected));

        testClient.get()
                .uri("/radio-program-info/latest-episode?programName=" + PROGRAM_NAME.getName())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(expected.getTitle())
                .jsonPath("$.description").isEqualTo(expected.getDescription())
                .jsonPath("$.publishDateUtc").isEqualTo(expected.getPublishDateUtc().toString());
    }

    @Test
    public void getLatestEpisode_returns400_whenProgramNameIsMissing() {
        testClient.get()
                .uri("/radio-program-info/latest-episode")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void getLatestEpisode_returns400_whenProgramNameIsEmpty() {
        testClient.get()
                .uri("/radio-program-info/latest-episode?programName=")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

}