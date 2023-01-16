package com.acme.fussballverein.rest;

import com.acme.fussballverein.entity.Adresse;
import com.acme.fussballverein.entity.Fussballverein;
import java.net.URL;
import java.time.LocalDate;

/**
 * Data Transfer Object für das Neuanlegen und Ändern eines Fussballvereines.
 *
 * @param vereinsname Gültiger Vereinsname.
 * @param email Gültige Email.
 * @param mannschaft Anzahl der Mannschaften.
 * @param entstehungsdatum Entstehungsdatum des Vereins.
 * @param homepage Homepage des Vereins.
 * @param adresse Adresse des Vereins.
 */
record FussballvereinDTO(
    String vereinsname,
    String email,
    int mannschaft,
    LocalDate entstehungsdatum,
    URL homepage,
    AdresseDTO adresse
) {
    Fussballverein toFussballverein() {
        final var adresseEntity = adresse() == null
            ? null
            : Adresse.builder()
                .id(null)
                .plz(adresse().postleitzahl())
                .ort(adresse().ort())
                .build();

        return Fussballverein.builder()
            .id(null)
            .version(0)
            .vereinsname(vereinsname)
            .email(email)
            .mannschaft(mannschaft)
            .entstehungsdatum(entstehungsdatum)
            .homepage(homepage)
            .adresse(adresseEntity)
            .erzeugt(null)
            .aktualisiert(null)
            .build();
    }
}
