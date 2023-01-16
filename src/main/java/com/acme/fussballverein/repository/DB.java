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

import com.acme.fussballverein.entity.Adresse;
import com.acme.fussballverein.entity.Fussballverein;
//import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
//import java.util.Collections;
//import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import static java.util.Locale.GERMANY;

/**
 * Emulation der Datenbasis für persistente Fußballvereine.
 */
@SuppressWarnings({"UtilityClassCanBeEnum", "UtilityClass", "MagicNumber", "RedundantSuppression"})
final class DB {
    /**
     * Liste der Fußballvereine zur Emulation der DB.
     */
    @SuppressWarnings("StaticCollection")
    static final List<Fussballverein> FUSSBALLVEREINE = getMitglied();

    private DB() {
    }


    @SuppressWarnings({"FeatureEnvy", "TrailingComment"})
    private static List<Fussballverein> getMitglied() {
        try {
            return Stream.of(
                    // admin
                    Fussballverein.builder()
                        .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                        .vereinsname("Admin")
                        .email("admin@acme.com")
                        .mannschaft(1)
                        .entstehungsdatum(LocalDate.parse("2022-01-31"))
                        .homepage(new URL("https://www.acme.com"))
                        .adresse(Adresse.builder().plz("00000").ort("Aachen").build())
                        .build(),
                    // HTTP GET
                    Fussballverein.builder()
                        .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                        .vereinsname("FC Bayern") //NOSONAR
                        .email("fcbayern@acme.de")
                        .mannschaft(2)
                        .entstehungsdatum(LocalDate.parse("2022-01-01"))
                        .homepage(new URL("https://www.acme.de"))
                        .adresse(Adresse.builder().plz("11111").ort("Augsburg").build())
                        .build(),
                    Fussballverein.builder()
                        .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                        .vereinsname("SC Freiburg")
                        .email("scfreiburg@acme.edu")
                        .mannschaft(1)
                        .entstehungsdatum(LocalDate.parse("2022-01-02"))
                        .homepage(new URL("https://www.acme.edu"))
                        .adresse(Adresse.builder().plz("22222").ort("Aalen").build())
                        .build(),
                    // HTTP PUT
                    Fussballverein.builder()
                        .id(UUID.fromString("00000000-0000-0000-0000-000000000030"))
                        .vereinsname("Karlsruhe")
                        .email("karlsruhe@acme.ch")
                        .mannschaft(3)
                        .entstehungsdatum(LocalDate.parse("2022-01-03"))
                        .homepage(new URL("https://www.acme.ch"))
                        .adresse(Adresse.builder().plz("33333").ort("Ahlen").build())
                        .build(),
                    // HTTP PATCH
                    Fussballverein.builder()
                        .id(UUID.fromString("00000000-0000-0000-0000-000000000040"))
                        .vereinsname("Delta")
                        .email("delta@acme.uk")
                        .mannschaft(4)
                        .entstehungsdatum(LocalDate.parse("2022-01-04"))
                        .homepage(new URL("https://www.acme.uk"))
                        .adresse(Adresse.builder().plz("44444").ort("Dortmund").build())
                        .build(),
                    // HTTP DELETE
                    Fussballverein.builder()
                        .id(UUID.fromString("00000000-0000-0000-0000-000000000050"))
                        .vereinsname("Epsilon")
                        .email("epsilon@acme.jp")
                        .mannschaft(2)
                        .entstehungsdatum(LocalDate.parse("2022-01-05"))
                        .homepage(new URL("https://www.acme.jp"))
                        .adresse(Adresse.builder().plz("55555").ort("Essen").build())
                        .build(),
                    // zur freien Verfuegung
                    Fussballverein.builder()
                        .id(UUID.fromString("00000000-0000-0000-0000-000000000060"))
                        .vereinsname("Phi")
                        .email("phi@acme.cn")
                        .mannschaft(3)
                        .entstehungsdatum(LocalDate.parse("2022-01-06"))
                        .homepage(new URL("https://www.acme.cn"))
                        .adresse(Adresse.builder().plz("66666").ort("Freiburg").build())
                        .build()
                )
                .collect(Collectors.toList());
        } catch (final MalformedURLException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
