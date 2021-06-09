package mikaeltenhunen.radioprograminfo.service;

import mikaeltenhunen.radioprograminfo.domain.Episode;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("radio-program-info")
public class ProgramInfoController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProgramInfoClient programInfoClient;

    public ProgramInfoController(ProgramInfoClient programInfoClient) {
        this.programInfoClient = programInfoClient;
    }

    @GetMapping("/latest-episode")
    public Mono<Episode> getLatestEpisode(@RequestParam ProgramName programName) {
        logger.info("In getLatestEpisode with programName={}", programName);
        // TODO handle null or empty programName
        // TODO get programId from cached map of programName to programId

        return programInfoClient.getAllPrograms()
                .doOnNext(nameToId -> logger.info("nameToId has size={}", nameToId.size()))
                .map(nameToId -> nameToId.get(programName))
                .flatMap(programInfoClient::getLatestEpisode);
        // TODO handle programName that does not exist in map of programs
    }
}
