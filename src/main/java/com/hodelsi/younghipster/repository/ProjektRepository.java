package com.hodelsi.younghipster.repository;

import com.hodelsi.younghipster.domain.Projekt;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Projekt entity.
 */
@SuppressWarnings("unused")
public interface ProjektRepository extends JpaRepository<Projekt,Long> {

}
