package com.acme.fussballverein.graphql;

/**
 * Record für Adresse als Inputtype.
 *
 * @param plz Postleitzahl.
 * @param ort Ort.
 */
@SuppressWarnings("Indentation")
public record AdresseInput(
    String plz,
    String ort
    ) {

    }
