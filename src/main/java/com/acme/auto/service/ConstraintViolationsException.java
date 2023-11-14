package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import java.util.Collection;

/**
 * Exception, falls es mindestens ein verletztes Constraint gibt.
 */
@Getter
public class ConstraintViolationsException extends RuntimeException {
    /**
     * Die verletzten Constraints.
     */
    private final transient Collection<ConstraintViolation<Auto>> violations;

    ConstraintViolationsException(final Collection<ConstraintViolation<Auto>> violations) {
        super("Constraints sind verletzt");
        this.violations = violations;
    }
}
