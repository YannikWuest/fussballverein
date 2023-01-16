package com.acme.fussballverein.rest;

public enum ProblemType {
    CONSTRAINTS("constraints"),
    UNPROCESSABLE("unprocessable"),
    PRECONDITION("precondition"),
    BAD_REQUEST("badRequest");

    private final String value;

    ProblemType(final String value) {
        this.value =value;
    }

    public String getValue() {
        return value;
    }
}
