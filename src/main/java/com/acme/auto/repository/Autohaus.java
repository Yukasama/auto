package com.acme.auto.repository;

import java.net.URI;

/**
 * Entity-Klasse für den REST-Client.
 *
 * @param name Name
 * @param homepage Homepage
 */
public record Autohaus (
    String name,
    URI homepage
) {}
