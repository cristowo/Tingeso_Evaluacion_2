package milkstgo.resultadoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ResultadoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResultadoServiceApplication.class, args);
	}

}
