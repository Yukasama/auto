package com.acme.auto.rest;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ValueObject für das Neuanlegen oder Ändern einer Reparatur
 * @param beschreibung Beschreibung der Reparatur
 * @param datum Datum der Reparatur
 * @param kosten Kosten der Reparatur
 */
record ReparaturDTO(
    String beschreibung,
    LocalDate datum,
    BigDecimal kosten
) {}
