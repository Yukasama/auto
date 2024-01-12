package com.acme.auto.rest;

import com.acme.auto.entity.FeatureType;
import com.acme.auto.entity.MarkeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * ValueObject für das Neuanlegen und Ändern eines neues Autos.
 * @param name Names des Autos
 * @param marke Marke des Autos
 * @param kennzeichen Gültiges Kennzeichen des Autos, d.h. mit passendem Muster
 * @param pferdeStaerke Pferdestärke des Autos
 * @param preis Preis des Autos
 * @param features Features des Autos
 * @param besitzer Besitzer des Autos als DTO
 * @param reparaturen Reparaturen des Autos als DTO
 */
record AutoDTO (
    UUID autohausId,
    String name,
    MarkeType marke,
    String kennzeichen,
    int pferdeStaerke,
    BigDecimal preis,
    List<FeatureType> features,
    BesitzerDTO besitzer,
    List<ReparaturDTO> reparaturen
) {}
