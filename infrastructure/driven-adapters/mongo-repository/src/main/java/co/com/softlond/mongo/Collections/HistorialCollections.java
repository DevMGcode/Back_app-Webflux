package co.com.softlond.mongo.Collections;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "historial")
public class HistorialCollections {
    private String id;
    private Integer contador;
    private String descripcion;
}
