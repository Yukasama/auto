package com.acme.auto.graphql;

import com.acme.auto.entity.Auto;
import com.acme.auto.service.AutoReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import static java.util.Collections.emptyMap;

/**
 * Controller für die Rückgabe von Autos der zuständig für GraphQL Queries ist
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AutoQueryController {
    private final AutoReadService service;

    /**
     * Suche anhand der Auto-ID
     * @param id ID des gesuchten Autos
     * @return Auto mit passender ID
     */
    @QueryMapping("auto")
    Auto findById(@Argument final UUID id) {
        log.debug("findById: id={}", id);
        final var auto = service.findById(id);
        log.debug("findById: {}", auto);
        return auto;
    }

    /**
     * Rückgabe von Autos anhand von Suchkriterien
     * @param input Suchkriterien für das Filtern von Autos
     * @return Alle gefundenen Autos
     */
    @QueryMapping("autos")
    Collection<Auto> find(@Argument final Optional<Suchkriterien> input) {
        log.debug("find: suchkriterien={}", input);
        final var suchkriterien = input.map(Suchkriterien::toMap)
            .orElse(emptyMap());
        final var autos = service.find(suchkriterien);
        log.debug("find: {}", autos);
        return autos;
    }
}
