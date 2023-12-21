package com.acme.auto.graphql;

import com.acme.auto.entity.Auto;
import com.acme.auto.entity.Besitzer;
import com.acme.auto.entity.Reparatur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/**
 * Mapper zwischen Entity-Klassen und GraphQL-Inputs
 */
@Mapper(componentModel = "spring",
    nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
interface AutoInputMapper {
    /**
     * Ein AutoInput-Objekt in ein Objekt für Auto konvertieren.
     * @param input AutoInput ohne ID
     * @return Konvertiertes Auto-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "featuresStr", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Auto toAuto(AutoInput input);

    /**
     * Ein BesitzerInput-Objekt in ein Objekt für Besitzer konvertieren.
     * @param input BesitzerInput ohne ID
     * @return Konvertiertes Besitzer-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    Besitzer toBesitzer(BesitzerInput input);

    /**
     * Ein ReparaturInput-Objekt in ein Objekt für Reparatur konvertieren.
     * @param input ReparaturInput ohne ID
     * @return Konvertiertes Reparatur-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    Reparatur toReparatur(ReparaturInput input);
}
