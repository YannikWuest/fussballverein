package com.acme.fussballverein.service;

import com.acme.fussballverein.entity.Fussballverein;
import com.acme.fussballverein.repository.FussballvereinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * Service Klasse für schreibenden Zugriff.
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class FussballvereinWriteService {
    private final FussballvereinRepository repo;
    private final Validator validator;

    /**
     * Einen neuen Fußballverein erstellen.
     *
     * @param fussballverein Fußballverein der angelegt werden soll
     * @return Der erstellte Fußballverein mit generierter UUID
     * @throws EmailExistsException Falls es die Emailadresse bereits gibt.
     * @throws ConstraintViolationsException Wenn einer oder mehrere Constraints verletzt sind.
     */
    @Transactional
    public Fussballverein create(final Fussballverein fussballverein) {
        log.debug("create: {}", fussballverein);
        final var violations = validator.validate(fussballverein);

        if (!violations.isEmpty()) {
            log.debug("create: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }
        if (repo.existsByEmail(fussballverein.getEmail())) {
            throw new EmailExistsException(fussballverein.getEmail());
        }

        final var fussballvereinDB = repo.save(fussballverein);
        log.debug("create: {}", fussballvereinDB);
        return fussballvereinDB;
    }

    @Transactional
    public Fussballverein update(final Fussballverein fussballverein, final UUID id, final int version) {
        log.debug("update: {}", fussballverein);
        log.debug("update: id={}, version={}", id, version);

        final var violations = validator.validate(fussballverein);
        if (!violations.isEmpty()) {
            log.debug("update: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }
        final var fussvalvereinOpt = repo.findById(id);
        if(fussvalvereinOpt.isEmpty()){
            throw new NotFoundException(id);
        }
        var fussballvereinDb = fussvalvereinOpt.get();
        if(version != fussballvereinDb.getVersion()) {
            throw new VersionOutdatedException(version);
        }

        final var email = fussballverein.getEmail();
        if(!Objects.equals(email, fussballvereinDb.getEmail()) && repo.existsByEmail(email)) {
            log.debug("update: email {} exstiert", email);
            throw new EmailExistsException(email);
        }

        fussballvereinDb.set(fussballverein);
        fussballvereinDb = repo.save(fussballvereinDb);
        log.debug("update: {}", fussballvereinDb);
        return fussballvereinDb;
    }
}
