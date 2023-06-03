package milkstgo.llegadaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LlegadaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LlegadaServiceApplication.class, args);
	}

}
