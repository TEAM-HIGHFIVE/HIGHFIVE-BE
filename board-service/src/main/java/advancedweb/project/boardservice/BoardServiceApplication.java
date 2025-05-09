package advancedweb.project.boardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients(basePackages = "advancedweb.project.boardservice.infra.client")
public class BoardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardServiceApplication.class, args);
    }

}
