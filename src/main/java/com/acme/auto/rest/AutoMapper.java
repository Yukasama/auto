package com.acme.auto.rest;

import com.acme.auto.entity.Auto;
import com.acme.auto.entity.Besitzer;
import com.acme.auto.entity.Reparatur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/**
 * Mapper zwischen Entity-Klassen und DTOs
 */
@Mapper(componentModel = "spring",
    nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
interface AutoMapper {
    /**
     * Ein DTO-Objekt von AutoDTO in ein Objekt für Auto konvertieren.
     *
     * @param dto DTO-Objekt für AutoDTO ohne ID
     * @return Konvertiertes Auto-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    Auto toAuto(AutoDTO dto);

    /**
     * Ein DTO-Objekt von BesitzerDTO in ein Objekt für Besitzer konvertieren.
     *
     * @param dto DTO-Objekt für BesitzerDTO ohne ID
     * @return Konvertiertes Besitzer-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    Besitzer toBesitzer(BesitzerDTO dto);

    /**
     * Ein DTO-Objekt von ReparaturDTO in ein Objekt für Reparatur konvertieren.
     *
     * @param dto DTO-Objekt für ReparaturDTO ohne ID
     * @return Konvertiertes Reparatur-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    Reparatur toReparatur(ReparaturDTO dto);
}
