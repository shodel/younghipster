package com.hodelsi.younghipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Mitarbeiter.
 */
@Entity
@Table(name = "mitarbeiter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mitarbeiter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "nachname")
    private String nachname;

    @Column(name = "rolle")
    private String rolle;

    @Column(name = "wochenstunden")
    private Integer wochenstunden;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public Integer getWochenstunden() {
        return wochenstunden;
    }

    public void setWochenstunden(Integer wochenstunden) {
        this.wochenstunden = wochenstunden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mitarbeiter mitarbeiter = (Mitarbeiter) o;
        if(mitarbeiter.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mitarbeiter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mitarbeiter{" +
            "id=" + id +
            ", vorname='" + vorname + "'" +
            ", nachname='" + nachname + "'" +
            ", rolle='" + rolle + "'" +
            ", wochenstunden='" + wochenstunden + "'" +
            '}';
    }
}
