package com.acme.fussballverein.rest;

import com.acme.fussballverein.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.Optional;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static com.acme.fussballverein.rest.FussballvereinGetController.ID_PATTERN;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;

/**
 * Eine `@RestController`-Klasse bildet die REST-Schnittstelle.
 */
@RestController
@Slf4j
@RequestMapping("/")
@Tag(name = "Fussballverein API")
@RequiredArgsConstructor
@SuppressWarnings("ClassFanOutComplexity")
public class FussballvereinWriteController {
    public static final String PROBLEM_PATH = "/problem/";
    private static final String VERSIONSNUMMER_FEHLT = "Versionsnummer fehlt";
    private final FussballvereinWriteService service;
    private final UriHelper uriHelper;

    /**
     * Einen neuen Fussballverein anlegen.
     *
     * @param fussballvereinDTO Data Transfer Object für den Fußballverein.
     * @param request Request Objekt im Response Header.
     * @return Statuscode 201 bei erfolgreichem anlegen.
     * @throws URISyntaxException bei Syntaktisch falscher URI
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Einen Fussballverein neu anlegen", tags = "Neuanlgegen")
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "400")
    @ApiResponse(responseCode = "422")
    ResponseEntity<Void> create(
        @RequestBody final FussballvereinDTO fussballvereinDTO,
        final HttpServletRequest request
    ) throws URISyntaxException {
        log.debug("create: {}", fussballvereinDTO);
        final var fussballvereinDb = service.create(fussballvereinDTO.toFussballverein());
        final var baseUri = uriHelper.getBaseUri(request);
        final var location = new URI(baseUri.toString() + '/' + fussballvereinDb.getId());
        return created(location).build();
    }

    /**
     * Update eines Fußballvereins mit gegebener ID.
     *
     * @param id ID des zu Aktualisierenden Fußballvereins.
     * @param fussballvereinDTO Data Transfer Object.
     * @return Statuscode 204 bei erfolgreichem Akutalisieren.
     */
    @PutMapping(path = "{id:" + ID_PATTERN + "}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Fußballverein aktualisieren", tags = "Aktualisieren")
    @ApiResponse(responseCode = "204")
    @ApiResponse(responseCode = "400")
    @ApiResponse(responseCode = "404")
    @ApiResponse(responseCode = "422")
    ResponseEntity<Void> update(
        @PathVariable final UUID id,
        @RequestBody final FussballvereinDTO fussballvereinDTO,
        @RequestHeader("If-Match") final Optional<String> version,
        final HttpServletRequest request
    ) {
        log.debug("update: id={}, {}", id, fussballvereinDTO);
        final int versionInt = getVersion(version, request);
        final var fussballverein = service.update(fussballvereinDTO.toFussballverein(), id, versionInt);
        return noContent().eTag("\"" + fussballverein.getVersion() + '"').build();
    }

    private int getVersion(final Optional<String> version, final HttpServletRequest request) {
        final var uri = URI.create(request.getRequestURL().toString());
        if (version.isEmpty()) {
            throw new VersionInvalidException(PRECONDITION_REQUIRED, VERSIONSNUMMER_FEHLT, uri);
        }
        final var versionStr = version.get();
        if(
            versionStr.length() < 3 ||
            versionStr.charAt(0) != '"' ||
            versionStr.charAt(versionStr.length() - 1) != '"'
        ) {
            throw new VersionInvalidException(PRECONDITION_FAILED, "Ungültiges ETag" + versionStr, uri);
        }
        final int versionInt;
        try{
            versionInt = Integer.parseInt(versionStr.substring(1, versionStr.length() - 1));
        } catch (final NumberFormatException ex) {
            throw new VersionInvalidException(
                PRECONDITION_FAILED,
                "Ungültiges ETag"  + versionStr,
                uri,
                ex
            );
        }
        return versionInt;
    }

    @ExceptionHandler(EmailExistsException.class)
    ResponseEntity<ProblemDetail> handleEmailExists(final EmailExistsException ex, final HttpServletRequest request) {
        log.debug("handleEmailExists: {}", ex.getMessage());
        final var problemDetail = ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, ex.getMessage());
        problemDetail.setType(URI.create("/problem/constraints"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(ConstraintViolationsException.class)
    ResponseEntity<ProblemDetail> handleConstrainViolations(final ConstraintViolationsException ex,
                                                            final HttpServletRequest request) {
        log.debug("handleConstraintViolation: {}", ex.getMessage());
        final var fussballvereinViolations = ex.getViolations()
            .stream()
            .map(constraintViolation -> ex.getMessage())
            .toList();
        final String detail = fussballvereinViolations.toString();

        final var problemDetail = ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, detail);
        problemDetail.setType(URI.create("/problem/constraints"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(VersionOutdatedException.class)
    ResponseEntity<ProblemDetail> handleVersionOutdated(
        final VersionOutdatedException ex,
        final HttpServletRequest request
    ) {
        log.debug("handleVersiounOutdaten: {}", ex.getMessage());
        final var problemDetail = ProblemDetail.forStatusAndDetail(PRECONDITION_FAILED, ex.getMessage());
        problemDetail.setType(URI.create(PROBLEM_PATH + ProblemType.PRECONDITION.getValue()));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(NotFoundException.class)
    @SuppressWarnings("unused")
    ResponseEntity<String> handleNotFound(final NotFoundException ex) {
        log.debug("handleNotFound: {}", ex.getMessage());
        return notFound().build();
    }
}
