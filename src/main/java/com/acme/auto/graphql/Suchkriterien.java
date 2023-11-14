package com.acme.auto.graphql;

import java.util.HashMap;
import java.util.Map;

record Suchkriterien (
    String name,

    String marke
) {
    /**
     * Konvertierung in eine Map.
     * @return Das konvertierte Map-Objekt
     */
    Map<String, String> toMap() {
        final Map<String, String> map = new HashMap<>(2, 1);
        if (name != null) {
            map.put("nachname", name);
        }
        if (marke != null) {
            map.put("email", marke);
        }
        return map;
    }
}