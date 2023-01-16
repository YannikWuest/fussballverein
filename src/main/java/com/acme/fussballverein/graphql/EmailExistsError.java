package com.acme.fussballverein.graphql;

import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.execution.ErrorType;

import java.util.List;

import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

/**
 * Email Exists Error.
 */
@RequiredArgsConstructor
public class EmailExistsError implements GraphQLError {
    private final String email;

    @Override
    public ErrorType getErrorType() {
        return BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "Die Emailadresse " + email + " ist ungültig";
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
