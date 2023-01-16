package com.acme.fussballverein.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import static com.acme.fussballverein.rest.FussballvereinGetController.REST_PATH;

/**
 * Hilfsklasse für URIs für HATEOAS zu ermitteln.
 */
@Component
@Slf4j
class UriHelper {
    private static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    private static final String X_FORWARDED_HOST = "x-forwarded-host";
    private static final String X_FORWARDED_PREFIX = "x-forwarded-prefix";
    private static final String FUSSBALLVEREIN_PREFIX = "/fussballverein";

    URI getBaseUri(final HttpServletRequest request) {
        final var fowardedHost = request.getHeader(X_FORWARDED_HOST);
        if (fowardedHost != null) {
            return getBaseUriFowarded(request, fowardedHost);
        }

        final var uriComponents = ServletUriComponentsBuilder.fromRequestUri(request).build();
        final var baseUriStr = uriComponents.getScheme() + "://" + uriComponents.getHost() + ':' +
            uriComponents.getPort() + REST_PATH;
        log.debug("getBaseUri: baseUriStr = {}", baseUriStr);
        final URI baseUri;
        try {
            baseUri = new URI(baseUriStr);
        } catch (final URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
        return baseUri;
    }

    private URI getBaseUriFowarded(final HttpServletRequest request, final String forwardedHost) {
        final var forwardedProto = request.getHeader(X_FORWARDED_PROTO);
        if (forwardedProto == null) {
            throw new IllegalStateException(X_FORWARDED_PROTO + "fehlt im Header");
        }
        var forwardedPrefix = request.getHeader(X_FORWARDED_PREFIX);
        if (forwardedPrefix == null) {
            log.trace("getBaseUriForwarded: Kein \"" + X_FORWARDED_PREFIX + "\" im Header");
            forwardedPrefix = FUSSBALLVEREIN_PREFIX;
        }
        final var baseUriStr = forwardedProto + "://" + forwardedHost + forwardedPrefix + REST_PATH;
        log.debug("getBaseUriForwarded: baseUriStr={}", baseUriStr);
        final URI baseUri;
        try {
            baseUri = new URI(baseUriStr);
        } catch (final URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
        return baseUri;
    }
}
