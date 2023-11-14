package com.acme.auto.graphql;

import com.acme.auto.entity.Auto;
import com.acme.auto.service.AutoReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyMap;

@Controller
@RequestMapping("/graphql")
@RequiredArgsConstructor
@Slf4j
public class AutoQueryController {
    private final AutoReadService service;

    /**
     * Suche anhand der Auto-ID
     * @param id ID des gesuchten Autos
     * @return Auto mit passender ID
     */
    @QueryMapping
    Auto auto(@Argument final UUID id) {
        log.debug(STR."auto: \{id}");
        final var auto = service.findById(id);
        log.debug(STR."auto: \{auto}");
        return auto;
    }

    /**
     * Rückgabe von Autos anhand von Suchkriterien
     * @param input Suchkriterien für das Filtern von Autos
     * @return Alle gefundenen Autos
     */
    @QueryMapping
    Collection<Auto> autos(@Argument final Optional<Suchkriterien> input) {
        log.debug(STR."autos: \{input}");
        final var suchkriterien = input.map(Suchkriterien::toMap)
            .orElse(emptyMap());
        final var autos = service.find(suchkriterien);
        log.debug(STR."autos: \{autos}");
        return autos;
    }

}
