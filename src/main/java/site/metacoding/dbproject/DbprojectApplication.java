package site.metacoding.dbproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // JpaAuditing 활성화
public class DbprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbprojectApplication.class, args);
	}

}
