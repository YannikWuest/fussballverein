package com.acme.fussballverein.graphql;

import com.acme.fussballverein.service.FussballvereinWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * Controller für graphql mutations.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class FussballvereinMutationController {
    /**
     * Service Objekt für den Aufruf des Anwendungskerns.
     */
    private final FussballvereinWriteService service;

    /**
     * Methode für das erstellen neuer Fussballvereine mit graphql.
     *
     * @param input Input type für Fusballverein.
     * @return Rückgabe von der ID des neuerstellten Fussballverein.
     */
    @MutationMapping
    CreatePayload create(@Argument final FussballvereinInput input) {
        log.debug("create:{}", input);
        final var id = service.create(input.toFussballverein()).getId();
        log.debug("create:{}", id);
        return new CreatePayload(id);
    }
}
