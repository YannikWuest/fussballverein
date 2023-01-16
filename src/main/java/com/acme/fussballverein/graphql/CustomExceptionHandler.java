package com.acme.fussballverein.graphql;

import com.acme.fussballverein.service.NotFoundException;
import com.acme.fussballverein.service.EmailExistsException;
import com.acme.fussballverein.service.ConstraintViolationsException;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Excetion Handler für errors in graphql API.
 */
@Component
public class CustomExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(
        @SuppressWarnings("NullableProblems")
        final Throwable ex,
        @SuppressWarnings("NullableProblems")
        final DataFetchingEnvironment env
    ) {
        if (ex instanceof final NotFoundException notFound) {
            return new NotFoundError(notFound.getId(), notFound.getSuchkriterien());
        } else if (ex instanceof final EmailExistsException emailExists) {
            return new EmailExistsError(emailExists.getEmail());
        }
        else if (ex instanceof final DateTimeParseException dateTimeParse){
            return new DateTimeParseError(dateTimeParse.getParsedString());
        }
        return super.resolveToSingleError(ex, env);
    }

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(
        @SuppressWarnings("NullableProblems")
        final Throwable ex,
        @SuppressWarnings("NullableProblems") final DataFetchingEnvironment env
    ) {
        if (ex instanceof final ConstraintViolationsException constraintVio) {
            return constraintVio.getViolations()
                .stream()
                .map(ConstraintViolationError::new)
                .collect(Collectors.toList());
        }
        return super.resolveToMultipleErrors(ex, env);
    }
}
