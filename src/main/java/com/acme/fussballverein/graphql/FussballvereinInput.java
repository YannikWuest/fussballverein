package com.acme.fussballverein.graphql;


import com.acme.fussballverein.entity.Adresse;
import com.acme.fussballverein.entity.Fussballverein;

import java.time.LocalDate;

/**
 * Record für Fussballverein als Input type.
 *
 * @param vereinsname      Vereinsname.
 * @param email            Email.
 * @param entstehungsdatum Entstehungsdatum.
 * @param mannschaft       Mannschaft.
 * @param homepage         Homepage.
 * @param adresse          Adresse.
 */
public record FussballvereinInput(
    String vereinsname,
    String email,
    String entstehungsdatum,
    Integer mannschaft,
    String homepage,
    AdresseInput adresse
) {
    /**
     * Methode zum konvertieren von Fussballvereinen in Java Objekt.
     *
     * @return Fertig gebautes Java Objekt.
     */
    Fussballverein toFussballverein() {
        final LocalDate entstehungsdatumTmp;
        entstehungsdatumTmp = LocalDate.parse(entstehungsdatum);
        final var adresseTmp = Adresse.builder().plz(adresse.plz()).ort(adresse.ort()).build();

        return Fussballverein
            .builder()
            .id(null)
            .vereinsname(vereinsname)
            .email(email)
            .entstehungsdatum(entstehungsdatumTmp)
            .mannschaft(mannschaft)
            .adresse(adresseTmp)
            .build();
    }
}
