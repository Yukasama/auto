package com.acme.auto.rest;

import com.acme.auto.service.AutoReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;

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
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = HAL_JSON_VALUE)
    @Operation(summary = "Suche nach Auto anhand ID", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "Auto gefunden")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    AutoModel getById(@PathVariable final UUID id) {
        log.debug("getById: id={}", id);
        final var auto = service.findById(id);

        final var model = new AutoModel(auto);
        final var baseUri = "https://localhost:8080/rest";
        final var idUri = STR."\{baseUri}/\{auto.getId()}";

        final var selfLink = Link.of(idUri);
        final var listLink = Link.of(baseUri, LinkRelation.of("list"));
        final var addLink = Link.of(baseUri, LinkRelation.of("add"));
        final var updateLink = Link.of(idUri, LinkRelation.of("update"));
        final var removeLink = Link.of(idUri, LinkRelation.of("remove"));

        model.add(selfLink, listLink, addLink, updateLink, removeLink);

        log.debug("getById: {}", auto);
        return model;
    }

    /**
     * Rückgabe von Autos anhand von Suchkriterien
     * @param suchkriterien Suchkriterien für das Filtern von Autos
     * @return Alle gefundenen Autos
     */
    @GetMapping(produces = HAL_JSON_VALUE)
    @Operation(summary = "Suche nach Auto anhand Suchkriterien", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "Auto gefunden")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    CollectionModel<AutoModel> get(@RequestParam MultiValueMap<String, String> suchkriterien) {
        log.debug("get: suchkriterien={}", suchkriterien);
        final var autos = service.find(suchkriterien);

        final var baseUri = "https://localhost:8080/rest";
        final var models = autos.stream()
            .map(auto -> {
                final var model = new AutoModel(auto);
                model.add(Link.of(STR."\{baseUri}/\{auto.getId()}"));
                return model;
            })
            .toList();

        log.debug("get: {}", autos);
        return CollectionModel.of(models);
    }
}
