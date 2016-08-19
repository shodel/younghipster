package com.hodelsi.younghipster.repository;

import com.hodelsi.younghipster.domain.Mitarbeiter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mitarbeiter entity.
 */
@SuppressWarnings("unused")
public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter,Long> {

}
