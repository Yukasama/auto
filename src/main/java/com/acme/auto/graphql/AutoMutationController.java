package com.acme.auto.graphql;

import com.acme.auto.service.AutoWriteService;
import com.acme.auto.service.KennzeichenExistsException;
import graphql.GraphQLError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

@Controller
@RequestMapping("/graphql")
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
        log.debug(STR."create: input=\{input}");
        final var autoMap = mapper.toAuto(input);
        final var id = service.create(autoMap).getId();
        log.debug(STR."create: id=\{id}");
        return new CreatePayload(id);
    }

    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    GraphQLError onKennzeichenExists(final KennzeichenExistsException ex) {
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(ex.getMessage())
            .build();
    }
}
