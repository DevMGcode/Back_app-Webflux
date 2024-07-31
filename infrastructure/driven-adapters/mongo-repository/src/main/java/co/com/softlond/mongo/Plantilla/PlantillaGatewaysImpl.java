package co.com.softlond.mongo.Plantilla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.com.softlond.model.PlantillaModel;
import co.com.softlond.model.gateways.PlantillaGateways;
import reactor.core.publisher.Mono;

@Repository
public class PlantillaGatewaysImpl implements PlantillaGateways {    

    @Override
    public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {
        System.out.println("Desde PlantillaGatewaysImpl.savePlantilla()");
        System.out.println(plantilla.getFechaActualizacion());
        throw new UnsupportedOperationException("Unimplemented method 'savePlantilla'");
    }
    
}
