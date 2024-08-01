package co.com.softlond.api.Historial;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.softlond.model.HistorialModel;
import co.com.softlond.usecase.Plantilla.HistorialOperationsUseCase;
import reactor.core.publisher.Mono;

@Component
public class HistorialHandler {
    
    private final HistorialOperationsUseCase historialOperationsUseCase;

    public HistorialHandler(HistorialOperationsUseCase historialOperationsUseCase) {
        this.historialOperationsUseCase = historialOperationsUseCase;
    }

    public Mono<ServerResponse> saveHistorial(ServerRequest request) {
        return request.bodyToMono(HistorialModel.class)
                .flatMap(historialOperationsUseCase::saveHistorial)
                .flatMap(historial -> ServerResponse.ok().bodyValue(historial))
                .switchIfEmpty(ServerResponse.noContent().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

    public Mono<ServerResponse> getHistorial(ServerRequest request) {
        return historialOperationsUseCase.getHistorial()
                .flatMap(historial -> ServerResponse.ok().bodyValue(historial))
                .switchIfEmpty(ServerResponse.noContent().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

}
