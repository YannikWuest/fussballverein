package com.acme.fussballverein.repository;

import lombok.Getter;
import org.springframework.graphql.client.GraphQlTransportException;
import org.springframework.web.reactive.function.client.WebClientException;

@Getter
public class MitgliedServiceException extends RuntimeException {
    private final WebClientException restExcetpion;
    private final GraphQlTransportException graphQlException;

    MitgliedServiceException(final WebClientException restExcetpion) {
        this.restExcetpion = restExcetpion;
        graphQlException = null;
    }

    MitgliedServiceException(final GraphQlTransportException graphQlException) {
        restExcetpion = null;
        this.graphQlException = graphQlException;
    }
}
