package com.acme.auto.repository;

import com.acme.auto.entity.Auto;
import com.acme.auto.entity.Auto_;
import com.acme.auto.entity.Besitzer_;
import com.acme.auto.entity.MarkeType;
import com.acme.auto.entity.Reparatur_;
import com.acme.auto.entity.FeatureType;
import com.acme.auto.entity.Reparatur;
import jakarta.persistence.criteria.Join;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class SpecificationBuilder {
    public Optional<Specification<Auto>> build(final Map<String, List<String>> suchkriterien) {
        log.debug("build: suchkriterien={}", suchkriterien);
        if (suchkriterien.isEmpty()) {
            return Optional.empty();
        }

        final var specs = suchkriterien
            .entrySet()
            .stream()
            .map(this::toSpecification)
            .toList();

        if (specs.isEmpty() || specs.contains(null)) {
            return Optional.empty();
        }

        return Optional.of(Specification.allOf(specs));
    }

    private Specification<Auto> toSpecification (final Map.Entry<String, List<String>> entry) {
        log.trace("toSpecification: entry={}", entry);
        final var key = entry.getKey();
        final var values = entry.getValue();

        if (Objects.equals(key, "feature")) {
            return toSpecificationFeatures(values);
        }
        if (values == null || values.size() != 1) {
            return null;
        }

        final var value = values.getFirst();
        return switch (key) {
            case "name" -> name(value);
            case "marke" -> marke(value);
            case "kennzeichen" -> kennzeichen(value);
            case "besitzerNachname" -> besitzerNachname(value);
            case "reparatur" -> reparaturen(value);
            default -> null;
        };
    }

    private Specification<Auto> toSpecificationFeatures(final Collection<String> features) {
        log.trace("build: features={}", features);
        if (features == null || features.isEmpty()) {
            return null;
        }

        final var featureSpecs = features.stream()
            .map(this::feature)
            .toList();
        if (featureSpecs.isEmpty() || featureSpecs.contains(null)) {
            return null;
        }

        final List<Specification<Auto>> specs = new ArrayList<>(featureSpecs);
        final var first = specs.removeFirst();
        return specs.stream().reduce(first, Specification::and);
    }

    private Specification<Auto> name(final String input) {
        return (root, _, builder) ->
            builder.like(builder.lower(root.get(Auto_.name)),
                         builder.lower(builder.literal(STR."%\{input}%")));
    }

    private Specification<Auto> marke(final String input) {
        return (root, _, builder) ->
            builder.equal(root.get(Auto_.marke), MarkeType.of(input));
    }

    private Specification<Auto> kennzeichen(final String input) {
        return (root, _, builder) ->
            builder.like(builder.lower(root.get(Auto_.kennzeichen)),
                builder.lower(builder.literal(STR."%\{input}%")));
    }

    private Specification<Auto> feature(final String input) {
        final var featureEnum = FeatureType.of(input);
        if (featureEnum == null) {
            return null;
        }
        return (root, _, builder) ->
            builder.like(builder.lower(root.get(Auto_.featuresStr)),
                           builder.literal(featureEnum.name()));
    }

    private Specification<Auto> besitzerNachname(final String input) {
        return (root, _, builder) ->
            builder.like(builder.lower(root.get(Auto_.besitzer).get(Besitzer_.nachname)),
                         builder.lower(builder.literal(STR."%\{input}%")));
    }

    private Specification<Auto> reparaturen(final String input) {
        return (root, _, builder) -> {
            Join<Auto, Reparatur> reparaturJoin = root.join(Auto_.reparaturen);
            return builder.like(builder.lower(reparaturJoin.get(Reparatur_.beschreibung)),
                builder.lower(builder.literal(STR."%\{input}%")));
        };
    }

}
