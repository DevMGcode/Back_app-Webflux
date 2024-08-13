package co.com.softlond.mongo.Plantilla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.com.softlond.model.PlantillaModel;
import co.com.softlond.model.gateways.PlantillaGateways;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Repository
public class PlantillaGatewaysImpl implements PlantillaGateways {    

    @Autowired
    private ReactivePlantillaMongoRepository reactivePlantillaMongoRepository;

    @Override
    public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {
        return reactivePlantillaMongoRepository.save(PlantillaMapper.toCollection(plantilla))
                .map(plantillaEntity -> PlantillaMapper.toModel(plantillaEntity));
    }

    @Override
    public Mono<PlantillaModel> getPlantillaById(String id) {
        return reactivePlantillaMongoRepository.findById(id)
                .map(PlantillaMapper::toModel);
    }

    @Override
    public Flux<PlantillaModel> getAllPlantillas() {
        return reactivePlantillaMongoRepository.findAll()
                .map(PlantillaMapper::toModel);
    }

    @Override
    public Mono<Void> deletePlantilla(String id) {
        return reactivePlantillaMongoRepository.deleteById(id);
    }

    
}
