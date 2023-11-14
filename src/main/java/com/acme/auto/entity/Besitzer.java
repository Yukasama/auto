package com.acme.auto.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

/**
 * Aufbau des Besitzers eines Autos
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter @Setter
@ToString
@SuppressWarnings({"JavadocDeclaration"})
public class Besitzer {
    /**
     * Die ID des Besitzers
     * @param id Besitzer ID
     * @return Besitzer ID
     */
    @EqualsAndHashCode.Include
    private UUID id;

    /**
     * Der Vorname des Besitzers
     * @param vorname Besitzer Vorname
     * @return Besitzer Vorname
     */
    @NotBlank
    private String vorname;

    /**
     * Der Nachname des Besitzers.
     * @param nachname Der Nachname.
     * @return Der Nachname.
     */
    @NotBlank
    private String nachname;
}
