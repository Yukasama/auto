package com.acme.auto.graphql;

import com.acme.auto.entity.Auto;
import com.acme.auto.service.ConstraintViolationsException;
import com.acme.auto.service.NotFoundException;
import graphql.GraphQLError;
import jakarta.validation.ConstraintViolation;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.util.Collection;
import java.util.stream.Collectors;
import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;
import static org.springframework.graphql.execution.ErrorType.NOT_FOUND;

/**
 * Abbildung von Exceptions auf GraphQLError
 */
@ControllerAdvice
final class ExceptionHandler {
    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    GraphQLError onNotFound(final NotFoundException ex) {
        final var id = ex.getId();
        final var message = id == null
            ? STR."Kein Auto gefunden: suchkriterien=\{ex.getSuchkriterien()}"
            : STR."Kein Auto mit der ID \{id} gefunden";

        return GraphQLError.newError()
            .errorType(NOT_FOUND)
            .message(message)
            .build();
    }

    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    Collection<GraphQLError> onConstraintViolations(final ConstraintViolationsException ex) {
        return ex.getViolations()
            .stream()
            .map(this::violationToGraphQLError)
            .collect(Collectors.toList());
    }

    private GraphQLError violationToGraphQLError(final ConstraintViolation<Auto> violation) {
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(violation.getMessage())
            .build();
    }
}
