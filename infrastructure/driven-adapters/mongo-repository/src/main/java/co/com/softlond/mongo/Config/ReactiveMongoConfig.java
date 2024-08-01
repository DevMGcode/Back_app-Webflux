package co.com.softlond.mongo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "co.com.softlond.mongo")
public class ReactiveMongoConfig {
}
