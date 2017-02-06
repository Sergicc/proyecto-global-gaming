package com.globalgaming.repository;

import com.globalgaming.domain.Etiqueta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Etiqueta entity.
 */
@SuppressWarnings("unused")
public interface EtiquetaRepository extends JpaRepository<Etiqueta,Long> {

}
