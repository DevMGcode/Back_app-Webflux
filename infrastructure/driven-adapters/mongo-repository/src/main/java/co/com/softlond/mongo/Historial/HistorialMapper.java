package co.com.softlond.mongo.Historial;

import co.com.softlond.model.HistorialModel;
import co.com.softlond.mongo.Collections.HistorialCollections;

public class HistorialMapper {
    public static HistorialModel toModel(HistorialCollections historialCollections) {
        HistorialModel historialModel = new HistorialModel();
        historialModel.setId(historialCollections.getId());
        historialModel.setContador(historialCollections.getContador());
        historialModel.setDescripcion(historialCollections.getDescripcion());
        return historialModel;
    }

    public static HistorialCollections toCollection(HistorialModel historialModel) {
        HistorialCollections historialCollections = new HistorialCollections();
        historialCollections.setId(historialModel.getId());
        historialCollections.setContador(historialModel.getContador());
        historialCollections.setDescripcion(historialModel.getDescripcion());
        return historialCollections;
    }
}
