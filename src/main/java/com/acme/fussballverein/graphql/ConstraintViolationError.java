package com.acme.fussballverein.graphql;

import com.acme.fussballverein.entity.Fussballverein;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.execution.ErrorType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

/**
 * Constraint Violation Error.
 */
@RequiredArgsConstructor
final class ConstraintViolationError implements GraphQLError {

    /**
     * Fussballverein mit Constraint Violations.
     */
    private final ConstraintViolation<Fussballverein> violation;

    @Override
    public ErrorType getErrorType() {
        return BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return violation.getMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public List<Object> getPath() {
        final List<Object> result = new ArrayList<>(5);
        result.add("input");
        for (final Node node : violation.getPropertyPath()) {
            result.add(node.toString());
        }
        return result;
    }
}
