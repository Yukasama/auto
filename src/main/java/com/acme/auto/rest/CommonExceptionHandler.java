package com.acme.auto.rest;

import com.acme.auto.service.ConstraintViolationsException;
import com.acme.auto.service.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.net.URI;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * Handler für allgemeine Exceptions in allen REST-Controllern.
 */
@ControllerAdvice
@Slf4j
class CommonExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    void onNotFound(final NotFoundException ex) {
        log.debug("onNotFound: {}", ex.getMessage());
    }

    @ExceptionHandler
    ProblemDetail onConstraintViolations(final ConstraintViolationsException ex, final HttpServletRequest request) {
        log.debug(STR."onConstraintViolation: \{ex.getMessage()}");

        final var violations = ex.getViolations().stream()
            .map(violation -> STR."\{violation} \n")
            .toList();
        log.trace(STR."onConstraintViolation: violations=\{violations.toString()}");

        final var problemDetail =
            ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, violations.toString());
        problemDetail.setType(URI.create("/problem/unprocessable"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }
}
