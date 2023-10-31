package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.acme.auto.repository.AutoRepository;
import java.util.Collection;
import java.util.UUID;

/**
 * Geschäftslogik für Auto
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AutoReadService {
    private final AutoRepository repo;

    /**
     * Auto anhand der ID suchen
     * @param id Die ID des gesuchten Autos
     * @return Das gefundene Auto
     * @throws NotFoundException Kein Auto mit ID wurde gefunden
     */
    public Auto findById(final UUID id) {
        log.debug("findById: id={}", id);
        final var auto = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(id));
        log.debug("findById: {}", auto);
        return auto;
    }

    /**
     * Alle Autos in der Datenbank abfragen
     * @return Alle Autos in der DB
     */
    public Collection<Auto> find() {
        final var autos = repo.findAll();
        log.debug("find: {}", autos);
        return autos;
    }
}
