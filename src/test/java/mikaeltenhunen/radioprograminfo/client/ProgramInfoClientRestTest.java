package mikaeltenhunen.radioprograminfo.client;

import mikaeltenhunen.radioprograminfo.domain.Episode;
import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

class ProgramInfoClientRestTest {

    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getAllPrograms() throws IOException {
        HttpUrl baseUrl = mockWebServer.url("");
        ProgramInfoClientRest target = new ProgramInfoClientRest(WebClient.create(), baseUrl.toString());
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(new String(Files.readAllBytes(Paths.get("src/test/resources/allPrograms.json"))))
        );

        Mono<Map<ProgramName, ProgramId>> allPrograms = target.getAllPrograms();
        StepVerifier
                .create(allPrograms)
                .expectNextMatches(map -> map.get(new ProgramName("Sporten P4 Skaraborg")).equals(new ProgramId(35)))
                .expectComplete()
                .verify();
    }

    @Test
    public void getLastEpisode() throws IOException {
        HttpUrl baseUrl = mockWebServer.url("");
        ProgramInfoClientRest target = new ProgramInfoClientRest(WebClient.create(), baseUrl.toString());
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(new String(Files.readAllBytes(Paths.get("src/test/resources/program35.json"))))
        );

        Mono<Episode> allPrograms = target.getLatestEpisode(new ProgramId(35));
        StepVerifier
                .create(allPrograms)
                .expectNext(new Episode(
                        "Med andra SM-finalen i handboll IKF Skövde - Sävehof",
                        "Sport, P4 Skaraborg, Sveriges Radio.",
                        MicrosoftDateConverter.convert("/Date(1621094580000)/")))
                .expectComplete()
                .verify();
    }

}