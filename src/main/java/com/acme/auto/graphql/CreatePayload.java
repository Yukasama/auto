package com.acme.auto.graphql;

import java.util.UUID;

/**
 * Value Klasse für das Payload bei einer GraphQL Mutation (Neuanlegen)
 * @param id ID des Autos
 */
record CreatePayload(
    UUID id
) {}
