package testchallenge.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class TestChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestChallengeApplication.class, args);
	}

}
