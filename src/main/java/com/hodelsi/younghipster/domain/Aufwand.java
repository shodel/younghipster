package com.hodelsi.younghipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Aufwand.
 */
@Entity
@Table(name = "aufwand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aufwand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "stunden")
    private Float stunden;

    @Column(name = "typ")
    private String typ;

    @ManyToOne
    private Projekt projekt;

    @ManyToOne
    private Mitarbeiter mitarbeiter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Float getStunden() {
        return stunden;
    }

    public void setStunden(Float stunden) {
        this.stunden = stunden;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Aufwand aufwand = (Aufwand) o;
        if(aufwand.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, aufwand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Aufwand{" +
            "id=" + id +
            ", datum='" + datum + "'" +
            ", stunden='" + stunden + "'" +
            ", typ='" + typ + "'" +
            '}';
    }
}
