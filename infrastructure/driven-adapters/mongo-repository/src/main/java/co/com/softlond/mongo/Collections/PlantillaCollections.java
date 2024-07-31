package co.com.softlond.mongo.Collections;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "plantilla")
public class PlantillaCollections {

    @Id
    private String id;
    private String nombre;
    private Date fecha;
    private String descripcion;
    private Date fechaActualizacion;
    
}
