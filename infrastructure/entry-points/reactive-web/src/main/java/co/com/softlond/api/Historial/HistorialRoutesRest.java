package co.com.softlond.api.Historial;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HistorialRoutesRest {
    
    @Bean
    public RouterFunction<ServerResponse> HistorialRoutes(HistorialHandler historialHandler) {
        return route(POST("/api/historial/save"), historialHandler::saveHistorial)
                .andRoute(GET("/api/historial/ver"), historialHandler::getHistorial);
    }
}
