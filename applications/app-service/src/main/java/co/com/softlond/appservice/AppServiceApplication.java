package co.com.softlond.appservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import reactor.core.publisher.Mono;

@SpringBootApplication
@ComponentScan(basePackages = {"co.com.softlond"})

public class AppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServiceApplication.class, args);

		Mono.just("Hello WebFlux!").subscribe(System.out::println);

	}
}