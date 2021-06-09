package mikaeltenhunen.radioprograminfo.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import mikaeltenhunen.radioprograminfo.domain.Episode;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("radio-program-info")
public class RadioProgramInfoController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProgramInfoFacade programInfoFacade;

    public RadioProgramInfoController(ProgramInfoFacade programInfoFacade) {
        this.programInfoFacade = programInfoFacade;
    }

    @GetMapping("/latest-episode")
    public Mono<Episode> getLatestEpisode(@RequestParam ProgramName programName) {
        logger.info("In getLatestEpisode with programName={}", programName);
        // TODO handle null or empty programName
        // TODO get programId from cached map of programName to programId

        return programInfoFacade.getAllPrograms()
                .doOnNext(nameToId -> logger.info("nameToId has size={}", nameToId.size()))
                .map(nameToId -> nameToId.get(programName))
                .flatMap(programInfoFacade::getLastBroadcast);
        // TODO handle programName that does not exist in map of programs
    }
}
