package com.acme.auto.repository;

import com.acme.auto.entity.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import static com.acme.auto.repository.DB.AUTOS;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository für den DB-Zugriff bei Autos
 */
@Repository
@Slf4j
@SuppressWarnings("PublicConstructor")
public class AutoRepository {
    /**
     * Auto nach seiner ID suchen
     * @param id Die ID des gesuchten Autos
     * @return Optionales Auto mit ID oder leeres Optional
     */
    public Optional<Auto> findById(final UUID id) {
        log.debug("findById: id={}", id);
        final var result = AUTOS.stream()
            .filter(auto -> Objects.equals(auto.getId(), id))
            .findFirst();
        log.debug("findById: {}", result);
        return result;
    }

    /**
     * Alle Autos zurückgeben
     * @return Alle Autos in der DB
     */
    public Collection<Auto> findAll() { return AUTOS; }
}
