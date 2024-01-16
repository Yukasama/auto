package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import com.acme.auto.repository.Autohaus;
import com.acme.auto.repository.AutohausRepository;
import com.acme.auto.repository.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.acme.auto.repository.AutoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Geschäftslogik für das Lesen von Autos
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AutoReadService {
    private final AutoRepository repo;
    private final AutohausRepository autohausRepo;
    private final SpecificationBuilder specBuilder;

    /**
     * Auto anhand der ID suchen
     * @param id Die ID des gesuchten Autos
     * @return Das gefundene Auto
     * @throws NotFoundException Kein Auto mit der ID wurde gefunden
     */
    public Auto findById(final UUID id) {
        log.debug("findById: id={}", id);
        final var auto = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(id));

        log.debug("LHKSADLÖJSAG, {}", auto);
        final var autohaus = findAutohausById(auto.getAutohausId());
        log.debug("LHKSADLÖJSAG, {}", autohaus);
        auto.setAutohausName(autohaus.name());
        auto.setAutohausHomepage(autohaus.homepage());

        log.debug("findById: {}", auto);
        return auto;
    }

    /**
     * Autos anhand Suchkriterien abfragen
     * @param suchkriterien Suchkriterien für Autos
     * @return Alle Autos entsprechend den Suchkriterien
     * @throws NotFoundException Kein Auto entspricht den Suchkriterien
     */
    public Collection<Auto> find(final Map<String, List<String>> suchkriterien) {
        log.debug("find: suchkriterien={}", suchkriterien);
        if (suchkriterien.isEmpty()) {
            return repo.findAll();
        }

        if (suchkriterien.size() == 1) {
            return findSingleCriteria(suchkriterien);
        }

        final var specs = specBuilder.build(suchkriterien)
            .orElseThrow(() -> new NotFoundException(suchkriterien));

        final var autos = repo.findAll(specs);
        if (autos.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }

        log.debug("find: {}", autos);
        return autos;
    }

    /**
     * Ausgelagerte Methode für die Rückgabe von Autos bei nur einem Suchkriterium
     * @param suchkriterien Suchkriterium
     * @return Auto-Objekt entsprechend dem Suchkriterium
     */
    private Collection<Auto> findSingleCriteria(final Map<String, List<String>> suchkriterien) {
        final var namen = suchkriterien.get("name");
        if (namen != null && namen.size() == 1) {
            final var autos = repo.findByName(namen.getFirst());
            if (autos.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (name): {}", autos);
            return autos;
        }

        final var marken = suchkriterien.get("marke");
        if (marken != null && marken.size() == 1) {
            final var autos = repo.findByMarke(marken.getFirst());
            if (autos.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (marke): {}", autos);
            return autos;
        }

        final var reparaturen = suchkriterien.get("reparatur");
        if (reparaturen != null && reparaturen.size() == 1) {
            final var autos = repo.findByReparatur(reparaturen.getFirst());
            if (autos.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (reparatur): {}", autos);
            return autos;
        }

        final var autohausIds = suchkriterien.get("autohausId");
        if (autohausIds != null && autohausIds.size() == 1) {
            final UUID autohausId = UUID.fromString(autohausIds.getFirst());
            final var autos = repo.findByAutohausId(autohausId);
            if(autos.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            final var autohaus = autohausRepo.getById(autohausId.toString());
            autos.forEach(auto -> {
                auto.setAutohausName(autohaus.name());
                auto.setAutohausHomepage(autohaus.homepage());
            });
            log.debug("find (autohausId): {}", autos);
            return autos;
        }

        throw new NotFoundException(suchkriterien);
    }

    private Autohaus findAutohausById(final UUID autohausId) {
        log.debug("findAutohausById: autohausId={}", autohausId);

        final var autohaus = autohausRepo.getById(autohausId.toString());

        log.debug("findAutohausById: {}", autohaus);
        return autohaus;
    }
}
