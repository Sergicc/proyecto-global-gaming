package com.globalgaming.repository;

import com.globalgaming.domain.Idioma;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Idioma entity.
 */
@SuppressWarnings("unused")
public interface IdiomaRepository extends JpaRepository<Idioma,Long> {

}
