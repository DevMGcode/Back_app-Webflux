package co.com.softlond.api.Plantilla;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.softlond.model.PlantillaModel;
import co.com.softlond.usecase.Plantilla.PlantillaOperationsUseCase;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class PlantillaHandler {
    
    private final PlantillaOperationsUseCase plantillaOperationsUseCase;

    public PlantillaHandler(PlantillaOperationsUseCase plantillaOperationsUseCase) {
        this.plantillaOperationsUseCase = plantillaOperationsUseCase;
    }



    public Mono<ServerResponse> savePlantilla(ServerRequest request) {
        System.out.println("PlantillaHandler.savePlantilla()");
        return request.bodyToMono(PlantillaModel.class)
                .flatMap(plantillaOperationsUseCase::savePlantilla)
                .doOnSuccess(plantilla -> System.out.println("Plantilla creada con exito")) // Agregado
                .flatMap(plantilla -> ServerResponse.ok().bodyValue(Map.of(
                        "mensaje", "Plantilla creada con exito",
                        "plantilla", plantilla
                )))

                .switchIfEmpty(ServerResponse.noContent().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(Map.of("error", error.getMessage())));
    }


    //mono get,dele

    public Mono<ServerResponse> getPlantillaById(ServerRequest request) {
        String id = request.pathVariable("id");
        System.out.println("Received ID: " + id);
        return plantillaOperationsUseCase.getPlantillaById(id)
                .flatMap(plantilla -> {
                    System.out.println("Found plantilla: " + plantilla);
                    return ServerResponse.ok().bodyValue(plantilla);
                })
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> {
                    System.err.println("Error: " + error.getMessage());
                    return ServerResponse.badRequest().bodyValue(error.getMessage());
                });
    }

    public Mono<ServerResponse> getAllPlantillas(ServerRequest request) {
        return ServerResponse.ok().body(plantillaOperationsUseCase.getAllPlantillas(), PlantillaModel.class)
                .doOnSuccess(plantillas -> System.out.println("Listado de las plantillas obtenido con exito"))
                .switchIfEmpty(ServerResponse.noContent().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

    public Mono<ServerResponse> updatePlantilla(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(PlantillaModel.class)
                .flatMap(plantilla -> plantillaOperationsUseCase.updatePlantilla(id, plantilla))
                .flatMap(updatedPlantilla -> {
                    // Crear un mapa para representar la respuesta JSON
                    Map<String, Object> response = Map.of(
                            "mensaje", "Plantilla actualizada con exito",
                            "plantilla", updatedPlantilla
                    );

                    // Imprimir el mensaje en consola en formato JSON
                    System.out.println("Plantilla actualizada con exito:\n" + response);

                    // Devolver la respuesta como JSON
                    return ServerResponse.ok().bodyValue(response);
                })
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(Map.of("error", error.getMessage())));
    }



    public Mono<ServerResponse> deletePlantilla(ServerRequest request) {
        String id = request.pathVariable("id");
        return plantillaOperationsUseCase.deletePlantilla(id)
                .then(ServerResponse.ok().bodyValue(Map.of("mensaje", "Plantilla eliminada con exito")))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }



}
