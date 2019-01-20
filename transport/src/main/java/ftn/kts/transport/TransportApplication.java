package ftn.kts.transport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class TransportApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportApplication.class, args);
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
			    System.out.println("USAO SAM OVDE I SVE JE OK \n\n\n");
				registry.addMapping("/rest/**").allowedOrigins("http://localhost:4200");
			}
		};
	}
}
