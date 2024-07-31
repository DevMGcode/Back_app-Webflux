package co.com.softlond.api.Plantilla;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.softlond.model.PlantillaModel;
import reactor.core.publisher.Mono;

@Component
public class PlantillaHandler {
    



    public Mono<ServerResponse> savePlantilla(ServerRequest request) {
        System.out.println("PlantillaHandler.savePlantilla()");
        return request.bodyToMono(PlantillaModel.class)
                .flatMap(plantilla -> ServerResponse.ok().bodyValue(plantilla))
                .switchIfEmpty(ServerResponse.noContent().build());
    }


}
