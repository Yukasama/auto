package com.acme.auto.rest;

import com.acme.auto.entity.Auto;
import com.acme.auto.entity.Besitzer;
import com.acme.auto.entity.MarkeType;
import com.acme.auto.entity.Reparatur;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import java.math.BigDecimal;
import java.util.List;

/**
 * Model-Klasse für Spring HATEOAS, um Links im Response-Body hinzuzufügen
 */
@Relation(collectionRelation = "autos", itemRelation = "auto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter @Setter
@ToString(callSuper = true)
class AutoModel extends RepresentationModel<AutoModel> {
    private final String name;
    private final MarkeType marke;

    @EqualsAndHashCode.Include
    private final String kennzeichen;

    private int pferdeStaerke;
    private BigDecimal preis;
    private Besitzer besitzer;
    private List<Reparatur> reparaturen;

    AutoModel(final Auto auto) {
        name = auto.getName();
        marke = auto.getMarke();
        kennzeichen = auto.getKennzeichen();
        pferdeStaerke = auto.getPferdeStaerke();
        preis = auto.getPreis();
        besitzer = auto.getBesitzer();
        reparaturen = auto.getReparaturen();
    }
}
