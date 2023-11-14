package com.acme.auto.rest;

import com.acme.auto.entity.Auto;
import com.acme.auto.service.AutoReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller für die Rückgabe von Autos der zuständig für GET-Requests ist
 */
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
@Slf4j
public class AutoGetController {
    /**
     * Muster für eine UUID. [\dA-Fa-f]{8}{8}-([\dA-Fa-f]{8}{4}-){3}[\dA-Fa-f]{8}{12} enthält eine "capturing group"
     * und ist nicht zulässig.
     */
    public static final String ID_PATTERN =
        "[\\dA-Fa-f]{8}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{12}";

    private final AutoReadService service;

    /**
     * Suche anhand der ID des Autos als Pfad-Parameter
     * @param id Die ID des gesuchten Autos
     * @return Auto mit passender ID
     */
    @SuppressWarnings("StringTemplateMigration")
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = APPLICATION_JSON_VALUE)
    Auto getById(@PathVariable final UUID id) {
        log.debug(STR."getById: id=\{id}");
        final var auto = service.findById(id);
        log.debug(STR."getById: \{auto}");
        return auto;
    }

    /**
     * Rückgabe von Autos anhand von Suchkriterien
     * @param suchkriterien Suchkriterien für das Filtern von Autos
     * @return Alle gefundenen Autos
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Collection<Auto> get(@RequestParam Map<String, String> suchkriterien) {
        log.debug(STR."get: suchkriterien=\{suchkriterien}");
        final var autos = service.find(suchkriterien);
        log.debug(STR."get: \{autos}");
        return autos;
    }
}
