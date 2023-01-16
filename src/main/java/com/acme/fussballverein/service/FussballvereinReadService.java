/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acme.fussballverein.service;

import com.acme.fussballverein.entity.Fussballverein;
import com.acme.fussballverein.repository.FussballvereinRepository;
import com.acme.fussballverein.repository.Mitglied;
import com.acme.fussballverein.repository.MitgliedRepository;
import com.acme.fussballverein.repository.MitgliedServiceException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Service.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FussballvereinReadService {

    private final FussballvereinRepository repo;
    private final MitgliedRepository mitgliedRepo;

    /**
     * Fußballverein zu Mitglied-Id suchen.
     *
     * @param mitgliedId Die Id des Mitglieds.
     * @return Den gefundenen Fußballverein.
     * @throws NotFoundException Falls kein Fußballverein gefunden wurde.
     */
    public Collection<Fussballverein> findByMitgliedId(final UUID mitgliedId) {
        log.debug("findByMitgliedId: mitgliedId={}", mitgliedId);

        final var fussballvereine = repo.findByMitgliedId(mitgliedId);
        if(fussballvereine.isEmpty()) {
            throw new NotFoundException();
        }

        final var mitglied = fetchMitgliedById(mitgliedId);
        final var nachname = mitglied == null ? null : mitglied.nachname();
        fussballvereine.forEach(fussballverein -> {
            fussballverein.setMitgliedNachname(nachname);
        });

        return fussballvereine;
    }

    private Mitglied fetchMitgliedById(final UUID mitgliedId) {
        log.debug("findMitgliedById: mitgliedId={}", mitgliedId);
        try {
            final var mitglied = mitgliedRepo
                .findById(mitgliedId)
                .orElse(new Mitglied("N/A"));
            log.debug("findMitgliedById: {}", mitglied);
            return mitglied;
        } catch (final MitgliedServiceException ex) {
            log.debug("findMitgliedById: {}", ex.getRestExcetpion().getClass().getSimpleName());
            return new Mitglied("Exception");
        }
    }

    /**
     * Einen Fußballverein mithilfe von angegebener UUID suchen.
     *
     * @param id UUID mit der gesucht werden soll
     * @return Einen gefundenen Fußballverein
     * @throws NotFoundException Falls zu angegebener UUID kein Fußballverein existiert.
     */
    public @NonNull Fussballverein findById(final UUID id) {
        log.debug("findById: id{}", id);
        final var fussballverein = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(id));
        log.debug("findById: {}", fussballverein);
        return fussballverein;
    }

    /**
     * Suchen nach Fußballvereinen mithilfe von Query-Paramtern.
     *
     * @param queryParams Die Query-Parameter gespeichert in einer Map
     * @return Die gefundenen Vereine
     * @throws NotFoundException Falls es zu angegebenen Query-Parametern keine Vereine gibt
     */
    public @NonNull Collection<Fussballverein> find(@NonNull final Map<String, List<String>> queryParams) {
        log.debug("find: suchkriterien={}", queryParams);

        if (queryParams.size() == 1) {
            final var nachnamen = queryParams.get("nachname");
            if (nachnamen != null && nachnamen.size() == 1) {
                final var fussballverein = repo.findByVereinsname(nachnamen.get(0));
                if(fussballverein.isEmpty()) {
                    throw new NotFoundException(queryParams);
                }
                log.debug("find mit nachname: {}", fussballverein);
                return fussballverein;
            }

            final var email = queryParams.get("email");
            if(email != null && email.size() == 1) {
                final var fussballverein = repo.findByEmail(email.get(0));
                if(fussballverein.isEmpty()) {
                    throw new NotFoundException(queryParams);
                }
                log.debug("find mit email: {}", fussballverein);
                final var fussballvereineList = List.of(fussballverein.get());
                return fussballvereineList;
            }
        }

        final var fussballvereine = repo.findAll();
        if (fussballvereine.isEmpty()) {
            throw new NotFoundException(queryParams);
        }
        log.debug("find: {}", fussballvereine);
        return fussballvereine;
    }

    /**
     * Welche Vereinsnamen passen zu gesuchtem Präfix.
     *
     * @param prefix Präfix, mit dem gesucht wird
     * @return Alle passenden Vereinsnamen
     * @throws NotFoundException Fallses zu gegebenem Präfix keine Vereinsnamen gibt
     */
    public @NonNull Collection<String> findVereinsnameByPrefix(final String prefix) {
        log.debug("findVereinsnameByPrefix: {}", prefix);
        final var vereinsnamen = repo.findVereinsnameByPrefix(prefix);
        if(vereinsnamen.isEmpty()) {
            throw new NotFoundException();
        }
        log.debug("findVereinsnameByPrefix: {}", vereinsnamen);
        return vereinsnamen;
    }
}
