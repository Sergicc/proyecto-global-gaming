package com.globalgaming.repository;

import com.globalgaming.domain.Sala;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sala entity.
 */
@SuppressWarnings("unused")
public interface SalaRepository extends JpaRepository<Sala,Long> {

}
