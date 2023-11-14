package com.acme.auto.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
     * Muster für ein deutsches Kennzeichen
     */
    public static final String KENNZEICHEN_PATTERN = "^[A-ZÄÖÜ]{1,3}-[A-Z]{1,2}-[1-9][0-9]{0,3}E?";

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
    @NotBlank
    private String name;

    /**
     * Die Marke des Autos
     * @param marke Automarke
     * @return Automarke
     */
    @NotNull
    private Marke marke;

    /**
     * Das Kennzeichen des Autos
     * @param kennzeichen Kennzeichen des Autos
     * @return Kennzeichen des Autos
     */
    @Pattern(regexp = KENNZEICHEN_PATTERN)
    private String kennzeichen;

    /**
     * Wie viel Pferdestärke hat das Auto
     * @param pferdeStaerke Die Pferdestärke
     * @return Die Pferdestärke
     */
    @Positive
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
    @Valid
    @ToString.Exclude
    private Besitzer besitzer;

    /**
     * Welche Reparaturen hatte das Auto
     * @param reparaturen Autoreparaturen
     * @return Autoreparaturen
     */
    @Valid
    @ToString.Exclude
    private List<Reparatur> reparaturen;
}
