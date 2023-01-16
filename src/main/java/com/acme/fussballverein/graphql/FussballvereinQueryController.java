package com.acme.fussballverein.graphql;

import com.acme.fussballverein.service.FussballvereinReadService;
import com.acme.fussballverein.entity.Fussballverein;
import java.util.Optional;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import static java.util.Collections.emptyMap;

/**
 * Controller für queries mit grahql.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class FussballvereinQueryController {
    /**
     * Service Objekt für Aufruf des Anwendungskerns.
     */
    private final FussballvereinReadService service;

    /**
     * Suchen eines Fussballvereins mit ID.
     *
     * @param id ID.
     * @return Gesuchter FUssballverein.
     */
    @QueryMapping
    Fussballverein fussballverein(@Argument final UUID id) {
        log.debug("findById: id={}", id);
        final var fussballverein = service.findById(id);
        log.debug("fussballverein: {}", fussballverein);
        return fussballverein;
    }

    /**
     * Suchen eines Fussballvereins mit Suchkriterien.
     *
     * @param input Suchkriterien als Map.
     * @return Gesuchte Fussballvereine.
     */
    @QueryMapping
    Collection<Fussballverein> fussballvereine(@Argument("input") final Optional<Suchkriterien> input) {
        log.debug("find: Suchkriterien={}", input);
        final var suchkriterien = input.map(Suchkriterien::toMap).orElse(emptyMap());
        final var fussballvereine = service.find(suchkriterien);
        log.debug("fussballvereine: {}", fussballvereine);
        return fussballvereine;
    }
}
