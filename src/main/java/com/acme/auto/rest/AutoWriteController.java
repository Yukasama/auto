package com.acme.auto.rest;

import com.acme.auto.service.AutoWriteService;
import com.acme.auto.service.ConstraintViolationsException;
import com.acme.auto.service.KennzeichenExistsException;
import com.acme.auto.service.VersionOutdatedException;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import static com.acme.auto.rest.AutoGetController.ID_PATTERN;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.PRECONDITION_REQUIRED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;

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
     *         verletzt sind oder das Kennzeichen bereits existiert oder Statuscode 400, falls syntaktische Fehler
     *         im Request-Body vorliegen.
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
     * @param autoDTO Das Autoobjekt aus dem eingegangenen Request-Body
     * @param versionOpt Versionsnummer aus dem Header If-Match
     * @param request Das Request-Objekt, um ggf. die URL für ProblemDetail zu ermitteln
     * @return Response mit Statuscode 204 oder Statuscode 400, falls der JSON-Datensatz syntaktisch nicht korrekt ist
     *         oder 422, falls Constraints verletzt sind oder die Emailadresse bereits existiert
     *         oder 412, falls die Versionsnummer nicht ok ist oder 428, falls die Versionsnummer fehlt.
     */
    @SuppressWarnings("StringTemplateMigration")
    @PutMapping(path = "{id:" + ID_PATTERN + "}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Vorhandenes Auto vollständig aktualisieren", tags = "Aktualisieren")
    @ApiResponse(responseCode = "204", description = "Auto aktualisiert")
    @ApiResponse(responseCode = "400", description = "Syntax der Request fehlerhaft")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    @ApiResponse(responseCode = "412", description = "Versionsnummer falsch")
    @ApiResponse(responseCode = "422", description = "Ungültige Werte oder Kennzeichen vorhanden")
    @ApiResponse(responseCode = "428", description = "Versionsnummer fehlt")
    ResponseEntity<Void> put(
        @PathVariable final UUID id,
        @RequestBody final AutoDTO autoDTO,
        @RequestHeader("If-Match") final Optional<String> versionOpt,
        final HttpServletRequest request
    ) {
        log.debug("put: dto={} id={}", autoDTO, id);
        final int version = getVersion(versionOpt, request);

        final var autoMap = mapper.toAuto(autoDTO);
        final var auto = service.update(autoMap, id, version);

        log.debug("put: {}", auto);
        return noContent().eTag(STR."\"\{auto.getVersion()}\"").build();
    }

    /**
     * Ausgelagerte Methode für Versionsprüfung
     * @param versionOpt Versionsnummer
     * @param request Request-Objekt für Problem URI Konstruktion
     * @return Geprüfte Versionsnummer
     */
    private int getVersion(final Optional<String> versionOpt, final HttpServletRequest request) {
        log.trace("getVersion: versionOpt={}", versionOpt);
        if (versionOpt.isEmpty()) {
            throw new VersionInvalidException(
                PRECONDITION_REQUIRED,
                "Versionsnummer fehlt",
                URI.create(request.getRequestURL().toString())
            );
        }

        final var versionStr = versionOpt.get();
        if (versionStr.length() < 3 ||
            versionStr.charAt(0) != '"' ||
            versionStr.charAt(versionStr.length() - 1) != '"'
        ) {
            throw new VersionInvalidException(
                PRECONDITION_FAILED,
                STR."Versionsnummer falsch: \{versionStr}",
                URI.create(request.getRequestURL().toString())
            );
        }

        final int version;
        try {
            version = Integer.parseInt(versionStr.substring(1, versionStr.length() - 1));
        } catch (final NumberFormatException ex) {
            throw new VersionInvalidException(
                PRECONDITION_FAILED,
                STR."Versionsnummer falsch: \{versionStr}",
                URI.create(request.getRequestURL().toString()),
                ex
            );
        }

        log.trace("getVersion: {}", version);
        return version;
    }

    /**
     * Einen vorhandenen Auto-Datensatz löschen
     * @param id Auto-ID
     */
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

    @ExceptionHandler
    ProblemDetail onVersionOutdated(final VersionOutdatedException ex, final HttpServletRequest request) {
        log.debug("onVersionOutdated: {}", ex.getMessage());
        final var problemDetail =
            ProblemDetail.forStatusAndDetail(PRECONDITION_FAILED, ex.getMessage());
        problemDetail.setType(URI.create("/problem/precondition-failed"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }
}
