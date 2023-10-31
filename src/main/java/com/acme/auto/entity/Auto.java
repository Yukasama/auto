package com.acme.auto.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Daten eines Autos. In DDD ist Auto ist ein Aggregate Root.
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter @Setter
@ToString
@SuppressWarnings({"JavadocDeclaration"})
public class Auto {
    /**
     * Die ID des Autos
     * @param id Auto ID
     * @return Auto ID
     */
    @EqualsAndHashCode.Include
    private UUID id;

    /**
     * Der Name des Autos
     * @param name Autoname
     * @return Autoname
     */
    private String name;

    /**
     * Die Marke des Autos
     * @param marke Automarke
     * @return Automarke
     */
    private Marke marke;

    /**
     * Wie viel Pferdestärke hat das Auto
     * @param pferdeStaerke Die Pferdestärke
     * @return Die Pferdestärke
     */
    private int pferdeStaerke;

    /**
     * Der Preis des Autos in €
     * @param preis Autopreis
     * @return Autopreis
     */
    private BigDecimal preis;

    /**
     * Wer besitzt das Auto
     * @param besitzer Autobesitzer
     * @return Autobesitzer
     */
    @ToString.Exclude
    private Besitzer besitzer;

    /**
     * Welche Reparaturen hatte das Auto
     * @param reparaturen Autoreparaturen
     * @return Autoreparaturen
     */
    @ToString.Exclude
    private List<Reparatur> reparaturen;
}
