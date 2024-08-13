package co.com.softlond.usecase.Plantilla;


import org.springframework.stereotype.Service;

import co.com.softlond.model.HistorialModel;
import co.com.softlond.model.PlantillaModel;
import co.com.softlond.model.gateways.PlantillaGateways;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.core.publisher.Flux;

@Service
public class PlantillaOperationsUseCase  {

    private final PlantillaGateways plantillaGateways;
    private final HistorialOperationsUseCase historialOperationsUseCase;

    public PlantillaOperationsUseCase(PlantillaGateways plantillaGateways, HistorialOperationsUseCase historialOperationsUseCase) {
        this.plantillaGateways = plantillaGateways;
        this.historialOperationsUseCase = historialOperationsUseCase;
    }

    /* public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {

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
    } */



    public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {
        if (plantilla.getId() == null) {

            return plantillaGateways.savePlantilla(plantilla)
                    .doOnSuccess(savedPlantilla -> saveHistorialAsync(savedPlantilla.getDescripcion(), true)
                            .subscribeOn(Schedulers.boundedElastic())
                            .subscribe());
        } else {

            return plantillaGateways.getPlantillaById(plantilla.getId())
                    .flatMap(existingPlantilla -> {
                        plantilla.setId(existingPlantilla.getId()); // Mantén el mismo ID
                        return plantillaGateways.savePlantilla(plantilla)
                                .doOnSuccess(updatedPlantilla -> saveHistorialAsync(updatedPlantilla.getDescripcion(), false)
                                        .subscribeOn(Schedulers.boundedElastic())
                                        .subscribe());
                    })
                    .switchIfEmpty(Mono.error(new RuntimeException("Plantilla no encontrada")));
        }
    }


    //Integer contador =1;
    //AtomicInteger contado2=0;
    //Metodo privado



    private Mono<Void> saveHistorialAsync(String descripcion, boolean isNew) {
        return historialOperationsUseCase.getHistorial()
                .defaultIfEmpty(new HistorialModel())
                .flatMap(history -> {

                    System.out.println("Historial actual: " + history);

                    if (isNew) {

                        if (history.getContador() == null) {
                            history.setContador(1);
                        } else {
                            history.setContador(history.getContador() + 1);
                        }
                    } else {

                        System.out.println("Actualizando descripción: " + descripcion);
                    }
                    history.setDescripcion(descripcion);
                    return historialOperationsUseCase.saveHistorial(history);
                })
                .then();
    }




    public Mono<PlantillaModel> getPlantillaById(String id) {
        return plantillaGateways.getPlantillaById(id)
                .doOnNext(plantilla -> System.out.println("Encontrar plantilla: " + plantilla))
                .doOnError(error -> System.err.println("Error no encontrada plantilla: " + error.getMessage()));
    }


    public Flux<PlantillaModel> getAllPlantillas() {
        return plantillaGateways.getAllPlantillas();
    }


    //---------------

    public Mono<PlantillaModel> updatePlantilla(String id, PlantillaModel plantilla) {
        // Verifica si el ID de la plantilla es válido
        if (id == null || plantilla == null) {
            return Mono.error(new IllegalArgumentException("ID o plantilla no pueden ser nulos"));
        }

        // Primero, obtén la plantilla existente por su ID
        return plantillaGateways.getPlantillaById(id)
                .flatMap(existingPlantilla -> {
                    // Actualiza los campos de la plantilla existente con los nuevos valores
                    plantilla.setId(id); // Asegúrate de que el ID es el correcto

                    // Guarda la plantilla actualizada
                    return plantillaGateways.savePlantilla(plantilla)
                            .flatMap(updatedPlantilla -> {
                                // Siempre actualiza la descripción en el historial, sin modificar el contador
                                return historialOperationsUseCase.getHistorial()
                                        .defaultIfEmpty(new HistorialModel())
                                        .flatMap(history -> {
                                            history.setDescripcion(updatedPlantilla.getDescripcion());
                                            return historialOperationsUseCase.saveHistorial(history);
                                        })
                                        .then(Mono.just(updatedPlantilla));
                            })
                            .doOnSuccess(updatedPlantilla -> {
                                System.out.println("Plantilla actualizada exitosamente: " + updatedPlantilla);
                                System.out.println("Nuevo estado de la plantilla: " + updatedPlantilla);
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Plantilla no encontrada"))); // Manejo de errores si la plantilla no existe
    }



    public Mono<Void> deletePlantilla(String id) {
        return plantillaGateways.getPlantillaById(id)
                .flatMap(plantilla -> {

                    return plantillaGateways.deletePlantilla(id)
                            .then(Mono.defer(() -> {
                                System.out.println("Plantilla eliminada: " + plantilla.getDescripcion());
                                return updateHistorialAfterDelete();// Resta 1 al contador y actualiza la descripción en el historial
                            }));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Plantilla no encontrada")));
    }



    private Mono<Void> updateHistorialAfterDelete() {
        return historialOperationsUseCase.getHistorial()
                .defaultIfEmpty(new HistorialModel())
                .flatMap(history -> {

                    if (history.getContador() != null && history.getContador() > 0) {
                        history.setContador(history.getContador() - 1);
                    }


                    return plantillaGateways.getAllPlantillas()
                            .collectList()
                            .flatMap(plantillas -> {
                                if (!plantillas.isEmpty()) {

                                    PlantillaModel ultimaPlantilla = plantillas.get(plantillas.size() - 1);
                                    history.setDescripcion(ultimaPlantilla.getDescripcion());
                                } else {

                                    history.setDescripcion(null);
                                }
                                return historialOperationsUseCase.saveHistorial(history);
                            });
                })
                .then();
    }


}