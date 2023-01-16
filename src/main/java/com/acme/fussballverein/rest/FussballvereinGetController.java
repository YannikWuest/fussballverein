package com.acme.fussballverein.rest;

import com.acme.fussballverein.service.FussballvereinReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.acme.fussballverein.service.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Objects;
import java.util.UUID;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static com.acme.fussballverein.rest.FussballvereinGetController.REST_PATH;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.ok;



/**
 * GetController.
 */
@RestController
@RequestMapping(REST_PATH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Fussballverein API")
final class FussballvereinGetController {

    static final String REST_PATH = "/rest/";
    static final String ID_PATTERN = "[\\dA-Fa-f]{8}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{12}";

    private final FussballvereinReadService service;
    private final UriHelper uriHelper;

    /**
     * Suche anhand der ID.
     *
     * @param id ID des Kunden.
     * @param request Request Objekt für HATEOAS.
     * @return Kunde mit passender ID.
     */
    @GetMapping(path = "{id:" + ID_PATTERN + "}",
        produces = HAL_JSON_VALUE)
    @Operation(summary = "Einen Fussballverein mit ID suchen", tags = "suchen")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404")
    ResponseEntity<FussballvereinModel> findById(
        @PathVariable final UUID id,
        @RequestHeader("If-None-Match") final Optional<String> version,
        final HttpServletRequest request
    ) {
        log.debug("findById: id={}", id);
        final var fussballverein = service.findById(id);

        final var currentVersion = "\"" + fussballverein.getVersion() + '"';
        if(Objects.equals(version.orElse(null), currentVersion)) {
            return status(NOT_MODIFIED).build();
        }

        //Hateos
        final var model = new FussballvereinModel(fussballverein);
        final var baseUri = uriHelper.getBaseUri(request).toString();
        final var idUri = baseUri + '/' + fussballverein.getId();
        final var selfLink = Link.of(idUri);
        final var listLink = Link.of(baseUri, LinkRelation.of("list"));
        final var addLink = Link.of(baseUri, LinkRelation.of("add"));
        final var updateLink = Link.of(idUri, LinkRelation.of("update"));
        final var removeLink = Link.of(idUri, LinkRelation.of("remove"));
        model.add(selfLink, listLink, addLink, updateLink, removeLink);

        log.debug("findById: {}", model);
        return ok(model);
    }

    /**
     * Suche mit Suchkriterien.
     *
     * @param allParams Map der Suchkriterien.
     * @param request Request Objekt für HATEOAS.
     * @return Gefundenen Kunden oder Gefundene Kunden als CollectionModel.
     */
    @GetMapping(produces = HAL_JSON_VALUE)
    @Operation(summary = "Einen Fussballverein mit queryparametern suchen", tags = "suchen")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404")
    @SuppressWarnings("Indentation")
    CollectionModel<FussballvereinModel> find(
        @RequestParam final MultiValueMap<String, String> allParams,
        final HttpServletRequest request
        ) {
        log.debug("find: allParams={}", allParams);
        final var baseUri = uriHelper.getBaseUri(request).toString();
        final var models = service.find(allParams)
            .stream()
            .map(fussballverein -> {
                final var model = new FussballvereinModel(fussballverein);
                model.add(Link.of(baseUri + "/" + fussballverein.getId()));
                return model;
            })
            .toList();
        log.debug("find: {}", models);
        return CollectionModel.of(models);
    }

    @GetMapping(path = "/vereinsname/{prefix}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Vereinsname mit Präfix suchen", tags = "suchen")
    String findNachnameByPrefix(@PathVariable final String prefix) {
        log.debug("findVereinsnamenByPrefix: {}", prefix);
        final var vereinsnamen = service.findVereinsnameByPrefix(prefix);
        log.debug("findVereinsnamenByPrefix: {}", vereinsnamen);
        return vereinsnamen.toString();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @SuppressWarnings("unused")
    void onNotFound(final NotFoundException ex) {
    }
}
