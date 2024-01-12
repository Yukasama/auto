package com.acme.auto.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import static org.springframework.http.HttpHeaders.IF_NONE_MATCH;

/**
 * "HTTP Interface" für den REST-Client für Autohaus-Daten.
 */
@HttpExchange("/rest")
public interface AutohausRepository {
    /**
     * Einen Autohaus-Datensatz vom Microservice "autohaus" anfordern.
     *
     * @param id ID des angeforderten Autohauses
     * @return Gefundenes Autohaus
     */
    @GetExchange("/{id}")
    Autohaus getById(@PathVariable String id);

    /**
     * Einen Autohaus-Datensatz vom Microservice "autohaus" anfordern.
     *
     * @param id ID des angeforderten Autohauses
     * @param version Version des angeforderten Datensatzes
     * @return Gefundenes Autohaus
     */
    @GetExchange("/{id}")
    @SuppressWarnings("unused")
    ResponseEntity<Autohaus> getById(
        @PathVariable String id,
        @RequestHeader(IF_NONE_MATCH) String version
    );
}
