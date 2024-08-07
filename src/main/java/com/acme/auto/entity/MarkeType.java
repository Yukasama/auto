package com.acme.auto.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enum für mögliche Marken eines Autos
 */
public enum MarkeType {
    /**
     * _Volkswagen_ mit dem internen Wert `VW`
     */
    VOLKSWAGEN("VW"),

    /**
     * _Mercedes_ mit dem internen Wert `M`
     */
    MERCEDES("M"),

    /**
     * _Ford_ mit dem internen Wert `F`
     */
    FORD("F"),

    /**
     * _Tesla_ mit dem internen Wert `T`
     */
    TESLA("T");

    private final String value;

    MarkeType(final String value) {
        this.value = value;
    }

    /**
     * Einen enum-Wert als String mit dem internen Wert ausgeben.
     * Dieser Wert wird durch Jackson in einem JSON-Datensatz verwendet.
     * @return Interner Wert
     */
    @Override
    @JsonValue
    public String toString() {
        return value;
    }

    /**
     * Konvertierung von String zu Enum-Wert
     * @param value String, welcher zu Enum-Wert konvertiert werden soll
     * @return Erfolgreich gefundener Enum-Wert oder null
     */
    @JsonCreator
    public static Optional<MarkeType> of(final String value) {
        return Stream.of(values())
            .filter(marke -> Objects.equals(marke.value, value))
            .findFirst();
    }
}
