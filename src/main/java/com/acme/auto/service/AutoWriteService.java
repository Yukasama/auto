package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import com.acme.auto.repository.AutoRepository;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

/**
 * Geschäftslogik für das Neuanlegen und Ändern von Autos
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Transactional
    public Auto create(final Auto auto) {
        log.debug("create: auto={}", auto);

        final var violations = validator.validate(auto);
        if (!violations.isEmpty()) {
            log.debug("create: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }

        final var kennzeichen = auto.getKennzeichen();
        if (repo.existsByKennzeichen(kennzeichen)) {
            throw new KennzeichenExistsException(kennzeichen);
        }

        final var autoDB = repo.save(auto);
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
     * @throws VersionOutdatedException Versionsnummer ist veraltet.
     * @throws KennzeichenExistsException Es gibt bereits ein Auto mit diesem Kennzeichen.
     */
    @Transactional
    public Auto update(final Auto auto, final UUID id, final int version) {
        log.debug("update: auto={} id={}", auto, id);

        final var violations = validator.validate(auto);
        if (!violations.isEmpty()) {
            log.debug("update: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }

        final var autoDBOpt = repo.findById(id);
        if (autoDBOpt.isEmpty()) {
            throw new NotFoundException(id);
        }

        var autoDB = autoDBOpt.get();
        final var versionDB = autoDB.getVersion();
        if (version != versionDB) {
            log.debug("update: Versionsnummern req={} db={} stimmt nicht überein.", version, versionDB);
            throw new VersionOutdatedException(version);
        }

        final var kennzeichen = auto.getKennzeichen();
        if (!autoDB.getKennzeichen().equals(kennzeichen) && repo.existsByKennzeichen(kennzeichen)) {
            log.debug("update: Kennzeichen {} existiert bereits.", kennzeichen);
            throw new KennzeichenExistsException(kennzeichen);
        }

        autoDB.set(auto);
        autoDB = repo.save(auto);
        log.debug("update: {} -> {}", autoDB, auto);
        return autoDB;
    }

    public void delete(final UUID id) {
        log.debug("delete: id={}", id);

        final var autoDBOpt = repo.findById(id);
        if (autoDBOpt.isEmpty()) {
            throw new NotFoundException(id);
        }

        repo.deleteById(id);
        log.debug("delete: {}", autoDBOpt);
    }
}
