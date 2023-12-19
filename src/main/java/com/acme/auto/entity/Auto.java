package com.acme.auto.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

/**
 * Daten eines Autos. In DDD ist Auto ist ein Aggregate Root.
 */
@Entity
@Table(name = "auto")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter @Setter
@ToString
@SuppressWarnings({"JavadocDeclaration"})
public class Auto {
    /**
     * Muster für ein deutsches Kennzeichen
     */
    public static final String KENNZEICHEN_PATTERN = "^[A-ZÄÖÜ]{1,3}-[A-Z]{1,2}-[1-9]\\d{0,3}E?";

    /**
     * Die ID des Autos
     * @param id Auto ID
     * @return Auto ID
     */
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    /**
     * Der Name des Autos
     * @param name Autoname
     * @return Autoname
     */
    @NotBlank
    private String name;

    /**
     * Die Marke des Autos
     * @param marke Automarke
     * @return Automarke
     */
    @NotNull
    @Enumerated(STRING)
    private MarkeType marke;

    /**
     * Das Kennzeichen des Autos
     * @param kennzeichen Kennzeichen des Autos
     * @return Kennzeichen des Autos
     */
    @NotNull
    @Pattern(regexp = KENNZEICHEN_PATTERN)
    private String kennzeichen;

    /**
     * Wie viel Pferdestärke hat das Auto
     * @param pferdeStaerke Die Pferdestärke
     * @return Die Pferdestärke
     */
    @Positive
    private int pferdeStaerke;

    /**
     * Der Preis des Autos
     * @param preis Autopreis
     * @return Autopreis
     */
    private BigDecimal preis;

    /**
     * Wer besitzt das Auto
     * @param besitzer Autobesitzer
     * @return Autobesitzer
     */
    @Valid
    @ToString.Exclude
    @OneToOne(cascade = {PERSIST, REMOVE},
              fetch = LAZY,
              orphanRemoval = true)
    private Besitzer besitzer;

    /**
     * Welche Reparaturen hatte das Auto
     * @param reparaturen Autoreparaturen
     * @return Autoreparaturen
     */
    @Valid
    @ToString.Exclude
    @OneToMany(cascade = {PERSIST, REMOVE},
               orphanRemoval = true)
    @JoinColumn(name = "auto_id")
    @OrderColumn(name = "idx")
    private List<Reparatur> reparaturen;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
