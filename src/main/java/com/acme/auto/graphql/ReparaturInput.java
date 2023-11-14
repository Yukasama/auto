package com.acme.auto.graphql;

import java.math.BigDecimal;
import java.time.LocalDate;

record ReparaturInput(
    String beschreibung,
    LocalDate datum,
    BigDecimal kosten
) {}
