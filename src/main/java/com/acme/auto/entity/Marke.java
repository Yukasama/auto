package com.acme.auto.entity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum für mögliche Marken eines Autos
 */
public enum Marke {
    /**
     * _Volkswagen_ mit dem internen Wert `VW` für z.B. das Mapping in einem JSON-Datensatz oder
     * das Abspeichern in einer DB.
     */
    VOLKSWAGEN("VW"),

    /**
     * _Mercedes_ mit dem internen Wert `M` für z.B. das Mapping in einem JSON-Datensatz oder
     * das Abspeichern in einer DB.
     */
    MERCEDES("M"),

    /**
     * _Ford_ mit dem internen Wert `F` für z.B. das
     * Mapping in einem JSON-Datensatz oder Abspeichern in einer DB.
     */
    FORD("F"),

    /**
     * _Tesla_ mit dem internen Wert `T` für z.B. das Mapping in einem JSON-Datensatz oder Abspeichern in einer DB.
     */
    TESLA("T");

    private final String value;

    Marke(final String value) {
        this.value = value;
    }

    /**
     * Einen enum-Wert als String mit dem internen Wert ausgeben.
     * Dieser Wert wird durch Jackson in einem JSON-Datensatz verwendet.
     * @return Interner Wert
     */
    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}
