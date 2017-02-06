package com.globalgaming.repository;

import com.globalgaming.domain.RelacionUsuarios;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RelacionUsuarios entity.
 */
@SuppressWarnings("unused")
public interface RelacionUsuariosRepository extends JpaRepository<RelacionUsuarios,Long> {

    @Query("select relacionUsuarios from RelacionUsuarios relacionUsuarios where relacionUsuarios.emisor.login = ?#{principal.username}")
    List<RelacionUsuarios> findByEmisorIsCurrentUser();

    @Query("select relacionUsuarios from RelacionUsuarios relacionUsuarios where relacionUsuarios.receptor.login = ?#{principal.username}")
    List<RelacionUsuarios> findByReceptorIsCurrentUser();

}
