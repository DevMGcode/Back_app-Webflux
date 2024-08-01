package co.com.softlond.mongo.Historial;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import co.com.softlond.mongo.Collections.HistorialCollections;

public interface ReactiveHistorialMongoRepository extends ReactiveMongoRepository<HistorialCollections, String> {
    
}
