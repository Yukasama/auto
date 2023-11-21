package com.acme.auto.rest;

import com.acme.auto.entity.MarkeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * ValueObject für das Neuanlegen und Ändern eines neues Autos.
 * @param name Names des Autos
 * @param marke Marke des Autos
 * @param kennzeichen Gültiges Kennzeichen des Autos, d.h. mit passendem Muster
 * @param pferdeStaerke Pferdestärke des Autos
 * @param preis Preis des Autos
 * @param besitzer Besitzer des Autos als DTO
 * @param reparaturen Reparaturen des Autos als DTO
 */
record AutoDTO (
    String name,
    MarkeType marke,
    String kennzeichen,
    int pferdeStaerke,
    BigDecimal preis,
    BesitzerDTO besitzer,
    List<ReparaturDTO> reparaturen
) {}
