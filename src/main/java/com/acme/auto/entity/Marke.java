package com.acme.auto.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
     * Konvertierung von String zu Enum-Wert
     * @param value String, welcher zu Enum-Wert konvertiert werden soll
     * @return Erfolgreich gefundener Enum-Wert oder null
     */
    public static Optional<Marke> of(final String value) {
        return Stream.of(values())
            .filter(marke -> Objects.equals(marke.value, value))
            .findFirst();
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
