package com.acme.fussballverein.graphql;

import graphql.language.SourceLocation;
import org.springframework.graphql.execution.ErrorType;
import graphql.GraphQLError;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

@RequiredArgsConstructor
public class DateTimeParseError implements GraphQLError {
   private final String parsedString;
    @Override
    public ErrorType getErrorType() {
        return BAD_REQUEST;
    }
    @Override
    public String getMessage(){
        return "Das Datum " + parsedString + "ist nicht korrekt.";
    }
    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
