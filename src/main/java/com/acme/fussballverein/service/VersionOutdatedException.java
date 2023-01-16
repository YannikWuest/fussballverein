package com.acme.fussballverein.service;

import lombok.Getter;

@Getter
public class VersionOutdatedException extends RuntimeException {
    private final int version;

    VersionOutdatedException(final int version){
        super("Die Versionsnummer ist veraltet");
        this.version = version;
    }
}
