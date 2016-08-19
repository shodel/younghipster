package com.hodelsi.younghipster.repository;

import com.hodelsi.younghipster.domain.Spesen;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Spesen entity.
 */
@SuppressWarnings("unused")
public interface SpesenRepository extends JpaRepository<Spesen,Long> {

}
