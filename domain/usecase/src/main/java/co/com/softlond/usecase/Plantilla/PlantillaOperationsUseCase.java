package co.com.softlond.usecase.Plantilla;

import java.sql.Date;

import org.springframework.stereotype.Service;

import co.com.softlond.model.HistorialModel;
import co.com.softlond.model.PlantillaModel;
import co.com.softlond.model.gateways.PlantillaGateways;
import reactor.core.publisher.Mono;

@Service
public class PlantillaOperationsUseCase  {
    
    private final PlantillaGateways plantillaGateways;
    private final HistorialOperationsUseCase historialOperationsUseCase;

    public PlantillaOperationsUseCase(PlantillaGateways plantillaGateways, HistorialOperationsUseCase historialOperationsUseCase) {
        this.plantillaGateways = plantillaGateways;
        this.historialOperationsUseCase = historialOperationsUseCase;
    }

    public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {
        
        plantilla.setFechaActualizacion(new Date(System.currentTimeMillis()));

        return plantillaGateways.savePlantilla(plantilla)
                .flatMap(savedPlantilla -> historialOperationsUseCase.getHistorial()
                        .defaultIfEmpty(new HistorialModel())
                        .flatMap(history -> {
                            history.setContador(null == history.getContador() ? 1 : history.getContador() + 1);
                            history.setDescripcion(savedPlantilla.getDescripcion());
                            return historialOperationsUseCase.saveHistorial(history);
                        })
                        .thenReturn(savedPlantilla));    
    }
}
