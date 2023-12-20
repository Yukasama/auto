package com.acme.auto.graphql;

import org.springframework.util.LinkedMultiValueMap;
import java.util.List;
import java.util.Map;

/**
 * Suchkriterien aus GraphQL Schema für das Filtern von Autos
 * @param name Name des Autos
 * @param marke Marke des Autos
 */
record Suchkriterien (
    String name,
    String marke,
    String reparatur
) {
    /**
     * Konvertierung in eine Map.
     * @return Das konvertierte Map-Objekt
     */
    Map<String, List<String>> toMap() {
        final Map<String, List<String>> map = new LinkedMultiValueMap<>();

        if (name != null) {
            map.put("name", List.of(name));
        }
        if (marke != null) {
            map.put("marke", List.of(marke));
        }
        if (reparatur != null) {
            map.put("reparatur", List.of(reparatur));
        }

        return map;
    }
}
