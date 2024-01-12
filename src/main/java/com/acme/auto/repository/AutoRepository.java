package com.acme.auto.repository;

import com.acme.auto.entity.Auto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository für den DB-Zugriff bei Autos
 */
@Repository
public interface AutoRepository extends JpaRepository<Auto, UUID>, JpaSpecificationExecutor<Auto> {
    @NonNull
    @Override
    @EntityGraph(attributePaths = {"besitzer", "reparaturen"})
    List<Auto> findAll();

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"besitzer", "reparaturen"})
    List<Auto> findAll(@NonNull Specification spec);

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"besitzer"})
    Optional<Auto> findById(@NonNull UUID id);

    /**
     * Alle Autos in einem Autohaus finden.
     * @param autohausId ID des Autohauses.
     * @return Alle Autos im Autohaus.
     */
    @EntityGraph(attributePaths = {"besitzer", "reparaturen"})
    List<Auto> findByAutohausId(UUID autohausId);

    /**
     * Auto anhand des Namen suchen
     * @param name Name des Autos
     * @return Alle gefundenen Autos
     */
    @Query("""
        SELECT a
        FROM Auto a
        WHERE lower(a.name) LIKE concat('%', lower(:name), '%')
        """)
    @EntityGraph(attributePaths = {"besitzer"})
    List<Auto> findByName(String name);

    /**
     * Auto anhand der Automarke suchen
     * @param markeStr Automarke des Autos
     * @return Alle gefundenen Autos
     */
    @Query("""
        SELECT a
        FROM Auto a
        WHERE lower(a.marke) = lower(:markeStr)
        """)
    @EntityGraph(attributePaths = {"besitzer"})
    Collection<Auto> findByMarke(String markeStr);

    /**
     * Auto anhand Beschreibung der Reparaturen suchen
     * @param beschreibung Beschreibung der Reparatur
     * @return Alle gefundenen Autos
     */
    @Query("""
        SELECT a
        FROM Auto a
        JOIN FETCH a.reparaturen r
        WHERE lower(r.beschreibung) = lower(:beschreibung)
        """)
    @EntityGraph(attributePaths = {"besitzer", "reparaturen"})
    Collection<Auto> findByReparatur(String beschreibung);

    /**
     * Existiert ein Kennzeichen bereits
     * @param kennzeichen Das Autokennzeichen
     * @return Existiert das Kennzeichen?
     */
    boolean existsByKennzeichen(String kennzeichen);
}
