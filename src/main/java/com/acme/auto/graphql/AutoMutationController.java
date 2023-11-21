package com.acme.auto.graphql;

import com.acme.auto.entity.Auto;
import com.acme.auto.service.AutoWriteService;
import com.acme.auto.service.ConstraintViolationsException;
import com.acme.auto.service.KennzeichenExistsException;
import graphql.GraphQLError;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

/**
 * Controller für die Rückgabe von Autos der zuständig für GraphQL Mutations ist
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AutoMutationController {
    private final AutoWriteService service;
    private final AutoInputMapper mapper;

    /**
     * Einen neuen Auto-Datensatz anlegen
     * @param input Eingabedaten für ein neues Auto als AutoInput
     * @return Generierte ID des neu angelegten Autos als CreatePayload
     */
    @MutationMapping
    CreatePayload create(@Argument final AutoInput input) {
        log.debug("create: input={}", input);
        final var autoMap = mapper.toAuto(input);
        final var id = service.create(autoMap).getId();
        log.debug("create: id={}", id);
        return new CreatePayload(id);
    }

    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    GraphQLError onKennzeichenExists(final KennzeichenExistsException ex) {
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(ex.getMessage())
            .path(Arrays.asList("input", "kennzeichen"))
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
            .path(Arrays.asList("input", violation.getPropertyPath().toString()))
            .build();
    }
}
