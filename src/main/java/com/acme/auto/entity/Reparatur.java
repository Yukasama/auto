package com.acme.auto.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Angaben zu Reparaturen eines Autos
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter @Setter
@ToString
@SuppressWarnings({"JavadocDeclaration"})
public class Reparatur {
    /**
     * Die ID der Reparatur
     * @param id Reparatur ID
     * @return Reparatur ID
     */
    @EqualsAndHashCode.Include
    private UUID id;

    /**
     * Was am Auto wurde repariert
     * @param beschreibung Reparatur Beschreibung
     * @return Reparatur Beschreibung
     */
    private String beschreibung;

    /**
     * An welchem Tag war die Reparatur
     * @param datum Reparatur Datum
     * @return Reparatur Datum
     */
    private LocalDate datum;

    /**
     * Kosten der Reparatur
     * @param kosten Reparaturkosten
     * @return Reparaturkosten
     */
    private BigDecimal kosten;
}
