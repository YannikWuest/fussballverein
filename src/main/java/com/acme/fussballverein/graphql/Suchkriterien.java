package com.acme.fussballverein.graphql;

import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Record für Suchkriterien.
 *
 * @param vereinsname Vereinsname.
 * @param email Email.
 */
public record Suchkriterien(
    String vereinsname,
    String email
) {
    /**
     * Methode um Suchkriterien in Map zu speichern.
     *
     * @return Suchkriterien als Map.
     */
    Map<String, List<String>> toMap() {
        final Map<String, List<String>> map = new LinkedMultiValueMap<>();
        if (vereinsname != null) {
            map.put("vereinsname", List.of(vereinsname));
        }
        if (email != null) {
            map.put("email", List.of(email));
        }
        return map;
    }
}
