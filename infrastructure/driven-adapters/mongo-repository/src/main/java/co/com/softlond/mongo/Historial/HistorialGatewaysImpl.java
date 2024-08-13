package co.com.softlond.mongo.Historial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.com.softlond.model.HistorialModel;
import co.com.softlond.model.gateways.HistorialGateways;
import co.com.softlond.mongo.Collections.HistorialCollections;
import reactor.core.publisher.Mono;

@Repository
public class HistorialGatewaysImpl implements HistorialGateways {
    
    @Autowired
    private ReactiveHistorialMongoRepository reactiveHistorialMongoRepository;

    @Override
    public Mono<HistorialModel> saveHistorial(HistorialModel historial) {
        return reactiveHistorialMongoRepository.save(HistorialMapper.toCollection(historial))
                .map(historialEntity -> HistorialMapper.toModel(historialEntity));
    }

    @Override
    public Mono<HistorialModel> getHistorial() {
        Mono<HistorialCollections> historial = reactiveHistorialMongoRepository.findAll().next();
        return historial.map(historialEntity -> HistorialMapper.toModel(historialEntity));
    }


}
