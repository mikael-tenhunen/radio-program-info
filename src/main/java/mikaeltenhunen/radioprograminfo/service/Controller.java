package mikaeltenhunen.radioprograminfo.service;

import mikaeltenhunen.radioprograminfo.domain.Episode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("radio-program-info")
public class Controller {

    @GetMapping("latest")
    //TODO max 3 concurrent requests
    public Episode getLatestBroadcast(@RequestParam String programName) {
        // TODO get programId from cached map of programName to programId
        // TODO handle null or empty programName
        // TODO handle programName that does not exist in map of programs
        // TODO return result
        return null;
    }
}
