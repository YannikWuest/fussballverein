package com.acme.fussballverein.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

/**
 * Daten eines Mitglieds.
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
@Entity
@Table(name = "fussballverein")
@AllArgsConstructor
@NoArgsConstructor
public class Fussballverein {
    /**
     * Muster für Vereinsnamen.
     */
    public static final String VEREINSNAME_PATTERN =
        "(FC|TSV|SC|SV)?[A-ZÄÖÜ][a-zäöüß]+";

    /**
     * Kleinster Wert für Mannschaftsnummer.
     */
    public static final int MIN_MANNSCHAFT = 1;

    /**
     * Größter Wert für Mannschaftsnummer.
     */
    public static final int MAX_MANNSCHAFT = 4;

    /**
     * Id eines Mitglieds.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Versionsnummer
     */
    @Version
    private int version;

    @Column(name = "mitglied_id")
    private UUID mitgliedId;

    /**
     * Nachname eines Mitglieds.
     */
    @NotEmpty
    @Pattern(regexp = VEREINSNAME_PATTERN)
    @Size(max = 40)
    private String vereinsname;

    /**
     * E-Mail des Vereins.
     */
    @Email
    @NotNull
    @Size(max = 40)
    private String email;

    /**
     * Die Mannschaften des Vereins.
     */
    @Min(MIN_MANNSCHAFT)
    @Max(MAX_MANNSCHAFT)
    private Integer mannschaft;

    /**
     * Das Entstehungsdatum des Vereins.
     */
    @Past
    private LocalDate entstehungsdatum;

    /**
     * Die URL zur Homepage des Vereins.
     */
    @Column(length = 40)
    private URL homepage;

    @OneToOne(optional = false, cascade = {PERSIST, REMOVE}, fetch = LAZY)
    @JoinColumn(updatable = false)
    @Valid
    @ToString.Exclude
    private Adresse adresse;

    @CreationTimestamp
    private LocalDateTime erzeugt;

    @UpdateTimestamp
    private LocalDateTime aktualisiert;

    @Transient
    private String mitgliedNachname;

    public void set(final Fussballverein fussballverein) {
        vereinsname = fussballverein.vereinsname;
        email = fussballverein.email;
        mannschaft = fussballverein.mannschaft;
        entstehungsdatum = fussballverein.entstehungsdatum;
        homepage = fussballverein.homepage;
    }
}
