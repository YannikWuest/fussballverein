package com.acme.fussballverein.graphql;

import org.springframework.graphql.execution.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.graphql.execution.ErrorType.NOT_FOUND;

/**
 * NotFoundError.
 */
@RequiredArgsConstructor
class NotFoundError implements GraphQLError {
    /**
     * Gesuchte ID.
     */
    private final UUID id;

    /**
     * Suchparameter.
     */
    private final Map<String, List<String>> allParams;


    @Override
    public String getMessage() {
        return id == null ? "Verein nicht gefunden: suchkriterien=" + allParams : id + "nicht gefunden";
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return NOT_FOUND;
    }
}
