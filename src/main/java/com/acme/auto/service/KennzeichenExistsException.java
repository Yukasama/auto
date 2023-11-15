package com.acme.auto.service;

import lombok.Getter;

/**
 * Exception falls es schon ein Auto mit demselben Kennzeichen gibt
 */
@Getter
public class KennzeichenExistsException extends RuntimeException {
    /**
     * Bereits vorhandenes Kennzeichen.
     */
    private final String kennzeichen;

    KennzeichenExistsException(final String kennzeichen) {
        super(STR."Das Kennzeichen \{kennzeichen} existiert bereits");
        this.kennzeichen = kennzeichen;
    }
}
