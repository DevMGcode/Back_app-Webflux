package co.com.softlond.mongo.Plantilla;

import org.springframework.stereotype.Repository;

import co.com.softlond.model.PlantillaModel;
import co.com.softlond.model.gateways.PlantillaGateways;
import reactor.core.publisher.Mono;

@Repository
public class PlantillaGatewaysImpl implements PlantillaGateways{

    @Override
    public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'savePlantilla'");
    }
    
}
