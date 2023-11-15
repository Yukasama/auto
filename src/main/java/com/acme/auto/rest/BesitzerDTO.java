package com.acme.auto.rest;

/**
 * ValueObject für das Neuanlegen oder Ändern eines Besitzers
 * @param vorname Vorname des Besitzers
 * @param nachname Nachname des Besitzers
 */
record BesitzerDTO(
    String vorname,
    String nachname
) {}
