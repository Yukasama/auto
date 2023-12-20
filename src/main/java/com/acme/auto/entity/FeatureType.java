package com.acme.auto.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

/**
 * Enum für mögliche Features eines Autos
 */
public enum FeatureType {
    /**
     * _Autonom_ mit dem internen Wert `A`, ob ein Auto autonom fahren kann
     */
    AUTONOM("A"),

    /**
     * _Sportmodus_ mit dem internen Wert `S`, ob ein Auto einen Sportmodus hat
     */
    SPORTMODUS("S"),

    /**
     * _Display_ mit dem internen Wert `D`, ob ein Auto ein eingebautes Display hat
     */
    DISPLAY("D");

    private final String value;

    FeatureType(final String value) { this.value = value; }

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
    public static FeatureType of(final String value) {
        return Stream.of(values())
            .filter(feature -> feature.value.equalsIgnoreCase(value))
            .findFirst()
            .orElse(null);
    }
}
