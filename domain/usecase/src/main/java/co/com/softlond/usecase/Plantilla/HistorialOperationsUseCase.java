package co.com.softlond.usecase.Plantilla;

import org.springframework.stereotype.Service;

import co.com.softlond.model.HistorialModel;
import co.com.softlond.model.gateways.HistorialGateways;
import reactor.core.publisher.Mono;

@Service
public class HistorialOperationsUseCase {
    private final HistorialGateways historialGateways;

    public HistorialOperationsUseCase(HistorialGateways historialGateways) {
        this.historialGateways = historialGateways;
    }

    public Mono<HistorialModel> saveHistorial(HistorialModel historial) {
        return historialGateways.saveHistorial(historial);
    }

    public Mono<HistorialModel> getHistorial() {
        return historialGateways.getHistorial();
    }
}
