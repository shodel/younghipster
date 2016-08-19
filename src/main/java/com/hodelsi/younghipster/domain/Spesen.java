package com.hodelsi.younghipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Spesen.
 */
@Entity
@Table(name = "spesen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Spesen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "posten")
    private String posten;

    @Column(name = "betrag")
    private Float betrag;

    @ManyToOne
    private Mitarbeiter mitarbeiter;

    @ManyToOne
    private Projekt projekt;

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

    public String getPosten() {
        return posten;
    }

    public void setPosten(String posten) {
        this.posten = posten;
    }

    public Float getBetrag() {
        return betrag;
    }

    public void setBetrag(Float betrag) {
        this.betrag = betrag;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Spesen spesen = (Spesen) o;
        if(spesen.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, spesen.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Spesen{" +
            "id=" + id +
            ", datum='" + datum + "'" +
            ", posten='" + posten + "'" +
            ", betrag='" + betrag + "'" +
            '}';
    }
}
