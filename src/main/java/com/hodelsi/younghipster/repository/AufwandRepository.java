package com.hodelsi.younghipster.repository;

import com.hodelsi.younghipster.domain.Aufwand;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Aufwand entity.
 */
@SuppressWarnings("unused")
public interface AufwandRepository extends JpaRepository<Aufwand,Long> {

}
