package com.acme.fussballverein.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MitgliedRepository {
    private final MitgliedRestRepository mitgliedRestRepository;

    public Optional<Mitglied> findById(final UUID mitgliedId) {
        final Mitglied mitglied;
        try{
            mitglied = mitgliedRestRepository.getMitglied(mitgliedId.toString()).block();
        } catch (final WebClientResponseException.NotFound ex) {
            log.error("findById: WebClientResponseException.NotFound");
            return Optional.empty();
        } catch (final WebClientException ex) {
            log.error("findById: {}", ex.getClass().getSimpleName());
            throw new MitgliedServiceException(ex);
        }
        log.debug("findById: {}", mitglied);
        return Optional.ofNullable(mitglied);
    }
}
