package com.acme.auto.graphql;

/**
 * Value Klasse für Inputdaten passend zu BesitzerInput aus GraphQL
 * @param vorname Vorname des Besitzers
 * @param nachname Nachname des Besitzers
 */
record BesitzerInput (
    String vorname,
    String nachname
) {}
