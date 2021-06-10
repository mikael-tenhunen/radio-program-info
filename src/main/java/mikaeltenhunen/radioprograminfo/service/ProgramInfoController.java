package mikaeltenhunen.radioprograminfo.service;

import mikaeltenhunen.radioprograminfo.domain.Episode;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import mikaeltenhunen.radioprograminfo.client.ProgramInfoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;

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
        validateProgramName(programName);

        return programInfoClient.getAllPrograms()
                .doOnNext(nameToId -> logger.info("nameToId has size={}", nameToId.size()))
                .map(nameToId -> nameToId.get(programName))
                .flatMap(programInfoClient::getLatestEpisode);
    }

    private void validateProgramName(ProgramName programName) {
        if (programName.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Program name can't be empty");
        }
    }
}
