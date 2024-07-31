package co.com.softlond.mongo.Plantilla;

import co.com.softlond.model.PlantillaModel;
import co.com.softlond.mongo.Collections.PlantillaCollections;

public class PlantillaMapper {
    
    public static PlantillaModel toModel(PlantillaCollections plantillaCollections) {
        return PlantillaModel.builder()
                .id(plantillaCollections.getId())
                .nombre(plantillaCollections.getNombre())
                .fecha(plantillaCollections.getFecha())
                .descripcion(plantillaCollections.getDescripcion())
                .fechaActualizacion(plantillaCollections.getFechaActualizacion())
                .build();
    }
    
    public static PlantillaCollections toCollection(PlantillaModel plantillaModel) {
        PlantillaCollections plantillaCollections = new PlantillaCollections();
        plantillaCollections.setId(plantillaModel.getId());
        plantillaCollections.setNombre(plantillaModel.getNombre());
        plantillaCollections.setFecha(plantillaModel.getFecha());
        plantillaCollections.setDescripcion(plantillaModel.getDescripcion());
        plantillaCollections.setFechaActualizacion(plantillaModel.getFechaActualizacion());
        return plantillaCollections;
    }
}
