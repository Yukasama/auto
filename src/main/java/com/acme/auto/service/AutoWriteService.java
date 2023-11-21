package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import com.acme.auto.repository.AutoRepository;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Geschäftslogik für das Neuanlegen und Ändern von Autos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AutoWriteService {
    private final AutoRepository repo;
    private final Validator validator;

    /**
     * Ein neues Auto anlegen.
     *
     * @param auto Objekt für das neue Auto.
     * @return Neu angelegtes Auto mit generierter ID
     * @throws ConstraintViolationsException Falls mindestens ein Constraint verletzt ist.
     * @throws KennzeichenExistsException Es gibt bereits ein Auto mit diesem Kennzeichen.
     */
    public Auto create(final Auto auto) {
        log.debug("create: auto={}", auto);

        final var violations = validator.validate(auto);
        if(!violations.isEmpty()) {
            log.debug("create: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }

        final var kennzeichen = auto.getKennzeichen();
        if(repo.isKennzeichenExisting(kennzeichen)) {
            throw new KennzeichenExistsException(kennzeichen);
        }

        final var autoDB = repo.create(auto);
        log.debug("create: {}", autoDB);
        return autoDB;
    }

    /**
     * Ein vorhandenes Auto aktualisieren.
     *
     * @param auto Das Objekt mit den neuen Daten (ohne ID)
     * @param id ID des zu aktualisierenden Autos
     * @throws ConstraintViolationsException Falls mindestens ein Constraint verletzt ist.
     * @throws NotFoundException Kein Auto zur ID vorhanden.
     * @throws KennzeichenExistsException Es gibt bereits ein Auto mit diesem Kennzeichen.
     */
    public void update(final Auto auto, final UUID id) {
        log.debug("update: auto={} id={}", auto, id);

        final var violations = validator.validate(auto);
        if(!violations.isEmpty()) {
            log.debug("update: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }

        final var autoDBOpt = repo.findById(id);
        if(autoDBOpt.isEmpty()) {
            throw new NotFoundException(id);
        }

        final var kennzeichen = auto.getKennzeichen();
        final var autoDB = autoDBOpt.get();
        if(!autoDB.getKennzeichen().equals(kennzeichen) && repo.isKennzeichenExisting(kennzeichen)) {
            log.debug("update: Kennzeichen {} existiert bereits.", kennzeichen);
            throw new KennzeichenExistsException(kennzeichen);
        }

        auto.setId(id);
        repo.update(auto);
        log.debug("update: {} -> {}", autoDB, auto);
    }
}
