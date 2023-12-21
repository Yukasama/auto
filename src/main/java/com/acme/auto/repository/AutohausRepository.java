/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.acme.auto.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.IF_NONE_MATCH;

/**
 * "HTTP Interface" für den REST-Client für Kundedaten.
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 */
@HttpExchange("/rest")
public interface AutohausRepository {
    /**
     * Einen Kundendatensatz vom Microservice "kunde" mit "Basic Authentication" anfordern.
     *
     * @param id ID des angeforderten Kunden
     * @param authorization String für den HTTP-Header "Authorization"
     * @return Gefundener Kunde
     */
    @GetExchange("/{id}")
    Autohaus getById(@PathVariable String id, @RequestHeader(AUTHORIZATION) String authorization);

    /**
     * Einen Kundendatensatz vom Microservice "kunde" mit "Basic Authentication" anfordern.
     *
     * @param id ID des angeforderten Kunden
     * @param version Version des angeforderten Datensatzes
     * @param authorization String für den HTTP-Header "Authorization"
     * @return Gefundener Kunde
     */
    @GetExchange("/{id}")
    @SuppressWarnings("unused")
    ResponseEntity<Autohaus> getById(
        @PathVariable String id,
        @RequestHeader(IF_NONE_MATCH) String version,
        @RequestHeader(AUTHORIZATION) String authorization
    );
}
