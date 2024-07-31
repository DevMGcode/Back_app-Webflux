package co.com.softlond.mongo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import co.com.softlond.mongo.Plantilla.ReactivePlantillaMongoRepository;

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = ReactivePlantillaMongoRepository.class)
public class ReactiveMongoConfig {
}
