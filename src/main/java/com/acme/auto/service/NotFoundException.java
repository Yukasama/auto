package com.acme.auto.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

/**
 * RuntimeException, falls kein Auto gefunden wurde.
 */
@Getter
public final class NotFoundException extends RuntimeException {
    /**
     * Nicht-vorhandene ID.
     */
    private final UUID id;

    /**
     * Suchkriterien, zu denen nichts gefunden wurde.
     */
    private final Map<String, List<String>> suchkriterien;

    NotFoundException(final UUID id) {
        super(STR."Kein Auto mit der ID \{id} gefunden.");
        this.id = id;
        this.suchkriterien = null;
    }

    NotFoundException(final Map<String, List<String>> suchkriterien) {
        super(STR."Keine Autos gefunden: suchkriterien=\{suchkriterien}");
        this.id = null;
        this.suchkriterien = suchkriterien;
    }
}
