package advancedweb.project.aiengineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("advancedweb.project.aiengineservice.infra.client")
public class AiEngineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiEngineServiceApplication.class, args);
	}

}
