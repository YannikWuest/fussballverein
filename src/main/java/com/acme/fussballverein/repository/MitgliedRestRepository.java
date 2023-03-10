package com.acme.fussballverein.repository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange("/rest")
@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public interface MitgliedRestRepository {
    @GetExchange("/{id}")
    Mono<Mitglied> getMitglied(@PathVariable String id);
}
