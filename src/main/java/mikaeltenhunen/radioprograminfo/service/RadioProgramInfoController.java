package mikaeltenhunen.radioprograminfo.service;

import mikaeltenhunen.radioprograminfo.domain.Episode;
import mikaeltenhunen.radioprograminfo.domain.ProgramId;
import mikaeltenhunen.radioprograminfo.domain.ProgramName;
import mikaeltenhunen.radioprograminfo.integration.ProgramInfoFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController()
public class RadioProgramInfoController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProgramInfoFacade programInfoFacade;

    public RadioProgramInfoController(ProgramInfoFacade programInfoFacade) {
        this.programInfoFacade = programInfoFacade;
    }

    @GetMapping("/latest")
    //TODO max 3 concurrent requests
    public Episode getLatestBroadcast(@RequestParam ProgramName programName) {
        logger.info("In getLatestBroadcast with programName={}", programName);
        // TODO handle null or empty programName
        // TODO get programId from cached map of programName to programId
        Map<ProgramName, ProgramId> toProgramId = programInfoFacade.getAllPrograms();
        logger.info("toProgramId has size={}", toProgramId.size());
        // TODO handle programName that does not exist in map of programs
        // TODO return result
        ProgramId id = Objects.requireNonNull(toProgramId.get(programName));
        return programInfoFacade.getLastBroadcast(id);
    }
}
