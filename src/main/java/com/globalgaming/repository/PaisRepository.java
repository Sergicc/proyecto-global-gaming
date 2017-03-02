package com.globalgaming.repository;

import com.globalgaming.domain.Pais;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pais entity.
 */
@SuppressWarnings("unused")
public interface PaisRepository extends JpaRepository<Pais,Long> {

}
