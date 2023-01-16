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
package com.acme.fussballverein.repository;

import com.acme.fussballverein.entity.Fussballverein;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.Collection;


/**
 * Repository.
 */
@Repository
public interface FussballvereinRepository extends JpaRepository<Fussballverein, UUID> {

    @EntityGraph(attributePaths = "adresse")
    @Override
    List<Fussballverein> findAll();

    @EntityGraph(attributePaths = "adresse")
    @Override
    Optional<Fussballverein> findById(UUID id);

    /**
     * Fußballverein mit dem Vereinsnamen suchen.
     *
     * @param vereinsname Vereinsname mit dem die suche durchgeführt wird
     * @return Die zu dem Vereinsnamen gefundenen Kunden. Falls es keine treffer in der DB gibt eine leere Collection
     */
    @Query(value = """
        SELECT f
        FROM fusballverein f
        WHERE lower(f.vereinsname) LIKE concat('%', lower(:vereinsname), '%')
        ORDER BY f.id
        """, nativeQuery = true)
    @EntityGraph(attributePaths = "adresse")
    Collection<Fussballverein> findByVereinsname(String vereinsname);

    /**
     * Überprüft, ob es zu gegebener E-mail einen Fußballverein gibt.
     *
     * @param email E-mail mit der die suche durchgeführt wird
     * @return true, falls ein Fußballverein vorhanden ist
     */
    boolean existsByEmail(String email);

    /**
     * Fußballverein mit der E-mail suchen.
     *
     * @param email E-mail mit der die suche durchgeführt wird
     * @return Den zu der angegebenen E-mail passende Fußballverein als Optional.
     */
    @Query(value = """
        SELECT f
        FROM fussballverein f
        WHERE lower(f.email) LIKE concat(lower(:email), '%')
        """, nativeQuery = true)
    @EntityGraph(attributePaths = "adresse")
    Optional<Fussballverein> findByEmail(String email);

    /**
     * Vereinsnamen zu angegebenem Präfix.
     *
     * @param prefix Präfix des Nachnamen
     * @return Die zu angegebenem Präfix gefundenen Nachnamen, oder eine leere Collection
     */
    @Query(value = """
        SELECT DISTINCT f.vereinsname
        FROM fussballverein f
        WHERE lower(f.vereinsname) LIKE concat(lower(:prefix), '%')
        ORDER BY f.vereinsname
        """, nativeQuery = true)
    Collection<String> findVereinsnameByPrefix(String prefix);

    @EntityGraph(attributePaths = "adresse")
    List<Fussballverein> findByMitgliedId(UUID mitgliedId);
}
