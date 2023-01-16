package com.acme.fussballverein.service;

import com.acme.fussballverein.entity.Fussballverein;
import jakarta.validation.ConstraintViolation;

import java.util.Collection;

import lombok.Getter;

/**
 * Constraint violation Exception.
 */
@Getter
public class ConstraintViolationsException extends RuntimeException {
    /**
     * Collection of constrain violations.
     */
    private final Collection<ConstraintViolation<Fussballverein>> violations;

    ConstraintViolationsException(final Collection<ConstraintViolation<Fussballverein>> violations) {
        super("Constraints sind verletzt");
        this.violations = violations;
    }
}
