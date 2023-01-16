package com.acme.fussballverein.service;

import lombok.Getter;

/**
 * EmailExistsExcepton falls Email bereits existiert.
 */
@Getter
public class EmailExistsException extends RuntimeException {
    /**
     * Email, welche schon existiert.
     */
    private final String email;
    EmailExistsException(final String email) {
        super("Die Email" + email + "existiert");
        this.email = email;
    }
}
