package com.acme.auto.rest;

import com.acme.auto.entity.Marke;
import java.math.BigDecimal;
import java.util.List;

/**
 * ValueObject für das Neuanlegen und Ändern eines neues Autos.
 * @param name Names des Autos
 * @param marke Marke des Autos
 * @param kennzeichen Gültiges Kennzeichen des Autos, d.h. mit passendem Muster
 * @param pferdeStaerke Pferdestärke des Autos
 * @param preis Preis des Autos
 * @param besitzer Besitzer des Autos
 * @param reparaturen Reparaturen des Autos
 */
record AutoDTO (
    String name,

    Marke marke,

    String kennzeichen,

    int pferdeStaerke,

    BigDecimal preis,

    BesitzerDTO besitzer,

    List<ReparaturDTO> reparaturen
) {}
