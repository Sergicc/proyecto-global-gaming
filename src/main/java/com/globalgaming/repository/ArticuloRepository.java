package com.globalgaming.repository;

import com.globalgaming.domain.Articulo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Articulo entity.
 */
@SuppressWarnings("unused")
public interface ArticuloRepository extends JpaRepository<Articulo,Long> {

    @Query("select distinct articulo from Articulo articulo left join fetch articulo.etiquetas")
    List<Articulo> findAllWithEagerRelationships();

    @Query("select articulo from Articulo articulo left join fetch articulo.etiquetas where articulo.id =:id")
    Articulo findOneWithEagerRelationships(@Param("id") Long id);

    //Articulo Repository prueba push carlos paús
    //prueba joan bosch

}
