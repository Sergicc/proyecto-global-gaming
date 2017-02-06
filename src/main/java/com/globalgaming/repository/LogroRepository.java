package com.globalgaming.repository;

import com.globalgaming.domain.Logro;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Logro entity.
 */
@SuppressWarnings("unused")
public interface LogroRepository extends JpaRepository<Logro,Long> {

}
