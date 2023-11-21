package com.acme.auto.graphql;

import com.acme.auto.service.NotFoundException;
import graphql.GraphQLError;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
}
