package mikaeltenhunen.radioprograminfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RadioProgramInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadioProgramInfoApplication.class, args);
	}

}
