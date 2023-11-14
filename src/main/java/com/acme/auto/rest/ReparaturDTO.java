package com.acme.auto.rest;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReparaturDTO(
    String beschreibung,

    LocalDate datum,

    BigDecimal kosten
) {}
