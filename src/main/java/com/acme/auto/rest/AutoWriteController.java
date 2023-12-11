package com.acme.auto.rest;

import com.acme.auto.service.AutoWriteService;
import com.acme.auto.service.ConstraintViolationsException;
import com.acme.auto.service.KennzeichenExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.net.URI;
import java.util.UUID;
import static com.acme.auto.rest.AutoGetController.ID_PATTERN;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

/**
 * Controller für die Rückgabe von Autos der zuständig für POST-, PUT-Requests ist
 */
@Controller
@RequestMapping("/rest")
@RequiredArgsConstructor
@Slf4j
public class AutoWriteController {
    private final AutoWriteService service;
    private final AutoMapper mapper;

    /**
     * Einen neuen Auto-Datensatz anlegen.
     *
     * @param autoDTO Das Autoobjekt aus dem eingegangenen Request-Body.
     * @param request Das Request-Objekt, um `Location` im Response-Header zu erstellen.
     * @return Response mit Statuscode 201 einschließlich Location-Header oder Statuscode 422, falls Constraints
     *      verletzt sind oder das Kennzeichen bereits existiert oder Statuscode 400, falls syntaktische Fehler
     *      im Request-Body vorliegen.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Neues Auto neuanlegen", tags = "Neuanlegen")
    @ApiResponse(responseCode = "201", description = "Auto neu angelegt")
    @ApiResponse(responseCode = "400", description = "Syntax der Request fehlerhaft")
    @ApiResponse(responseCode = "422", description = "Ungültige Werte oder Kennzeichen vorhanden")
    ResponseEntity<Void> post(@RequestBody final AutoDTO autoDTO, final HttpServletRequest request) {
        log.debug("post: dto={}", autoDTO);
        final var autoMap = mapper.toAuto(autoDTO);
        final var autoDB = service.create(autoMap);
        final var location =
            URI.create(STR."\{request.getRequestURL()}/\{autoDB.getId()}");
        return created(location).build();
    }

    /**
     * Einen vorhandenen Auto-Datensatz überschreiben.
     *
     * @param id ID des zu aktualisierenden Autos.
     * @param autoDTO Das Autoobjekt aus dem eingegangenen Request-Body.
     */
    @SuppressWarnings("StringTemplateMigration")
    @PutMapping(path = "{id:" + ID_PATTERN + "}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Vorhandenes Auto vollständig aktualisieren", tags = "Aktualisieren")
    @ApiResponse(responseCode = "204", description = "Auto aktualisiert")
    @ApiResponse(responseCode = "400", description = "Syntax der Request fehlerhaft")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    @ApiResponse(responseCode = "422", description = "Ungültige Werte oder Kennzeichen vorhanden")
    void put(@PathVariable final UUID id, @RequestBody final AutoDTO autoDTO) {
        log.debug("put: dto={} id={}", autoDTO, id);
        final var autoMap = mapper.toAuto(autoDTO);
        service.update(autoMap, id);
    }

    @SuppressWarnings("StringTemplateMigration")
    @DeleteMapping(path = "{id:" + ID_PATTERN + "}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Vorhandenes Auto löschen", tags = "Löschen")
    @ApiResponse(responseCode = "204", description = "Auto gelöscht")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    void delete(@PathVariable final UUID id) {
        log.debug("delete: id={}", id);
        service.delete(id);
    }

    @ExceptionHandler
    ProblemDetail onKennzeichenExists(final KennzeichenExistsException ex, final HttpServletRequest request) {
        log.debug("onKennzeichenExists: {}", ex.getMessage());
        final var problemDetail =
            ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, ex.getMessage());
        problemDetail.setType(URI.create("/problem/unprocessable"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }

    @ExceptionHandler
    ProblemDetail onConstraintViolations(final ConstraintViolationsException ex, final HttpServletRequest request) {
        log.debug("onConstraintViolation: {}", ex.getMessage());

        final var violations = ex.getViolations().stream()
            .map(violation -> STR."\{violation} \n")
            .toList();
        log.trace("onConstraintViolation: violations={}", violations);

        final var problemDetail =
            ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, violations.toString());
        problemDetail.setType(URI.create("/problem/unprocessable"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }
}
