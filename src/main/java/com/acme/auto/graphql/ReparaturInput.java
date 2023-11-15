package com.acme.auto.graphql;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Value Klasse mit Inputdaten für ReparaturInput aus GraphQL Schema
 * @param beschreibung Beschreibung der Reparatur
 * @param datum Datum der Reparatur
 * @param kosten Kosten der Reparatur
 */
record ReparaturInput(
    String beschreibung,
    LocalDate datum,
    BigDecimal kosten
) {}
