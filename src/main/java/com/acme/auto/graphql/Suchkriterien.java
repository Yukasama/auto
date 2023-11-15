package com.acme.auto.graphql;

import java.util.HashMap;
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
    Map<String, String> toMap() {
        final Map<String, String> map = new HashMap<>(3);
        if (name != null) {
            map.put("name", name);
        }
        if (marke != null) {
            map.put("marke", marke);
        }
        if (reparatur != null) {
            map.put("reparatur", reparatur);
        }
        return map;
    }
}
