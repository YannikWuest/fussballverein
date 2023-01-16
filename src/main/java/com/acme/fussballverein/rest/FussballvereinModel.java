package com.acme.fussballverein.rest;

import com.acme.fussballverein.entity.Adresse;
import com.acme.fussballverein.entity.Fussballverein;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import java.net.URL;
import java.time.LocalDate;

/**
 * Model-Klasse für Spring HATEOAS.
 */
@Relation(collectionRelation = "fussballvereine", itemRelation = "fussballverein")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class FussballvereinModel extends RepresentationModel<FussballvereinModel> {

    private final String vereinsname;
    @EqualsAndHashCode.Include
    private final String email;

    private final int mannschaft;
    private final LocalDate entstehungsdatum;
    private final URL homepage;
    private final Adresse adresse;

    FussballvereinModel(final Fussballverein fussballverein) {
        vereinsname = fussballverein.getVereinsname();
        email = fussballverein.getEmail();
        mannschaft = fussballverein.getMannschaft();
        entstehungsdatum = fussballverein.getEntstehungsdatum();
        homepage = fussballverein.getHomepage();
        adresse = fussballverein.getAdresse();
    }
}
