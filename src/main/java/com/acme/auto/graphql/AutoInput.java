package com.acme.auto.graphql;

import com.acme.auto.entity.MarkeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * Eine Value-Klasse für Input-Daten passend zu AutoInput aus GraphQL-Schema
 * @param name Name des Autos
 * @param marke Marke des Autos
 * @param kennzeichen Kennzeichen des Autos
 * @param pferdeStaerke Pferdestärke des Autos
 * @param preis Preis des Autos
 * @param besitzer Besitzer des Autos
 * @param reparaturen Reparaturen des Autos
 */
record AutoInput (
    String name,
    MarkeType marke,
    String kennzeichen,
    int pferdeStaerke,
    BigDecimal preis,
    BesitzerInput besitzer,
    List<ReparaturInput> reparaturen
) {}
