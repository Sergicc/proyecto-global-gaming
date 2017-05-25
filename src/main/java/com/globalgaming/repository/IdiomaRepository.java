package com.globalgaming.repository;

import com.globalgaming.domain.Idioma;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Idioma entity.
 */
@SuppressWarnings("unused")
public interface IdiomaRepository extends JpaRepository<Idioma,Long> {

    @Query ("select distinct idioma.nombre from Idioma idioma")
    Page<String> findAllDistinct(Pageable pageable);

}
