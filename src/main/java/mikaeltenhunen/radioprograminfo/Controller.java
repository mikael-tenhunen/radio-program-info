package mikaeltenhunen.radioprograminfo;

import mikaeltenhunen.radioprograminfo.domain.Broadcast;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("radio-program-info")
public class Controller {

    @GetMapping("latest")
    public Broadcast getLatestBroadcast(@RequestParam String programName) {

        // TODO
        return null;
    }
}
