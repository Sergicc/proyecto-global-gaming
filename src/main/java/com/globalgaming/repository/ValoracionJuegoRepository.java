package com.globalgaming.repository;

import com.globalgaming.domain.Juego;
import com.globalgaming.domain.ValoracionJuego;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ValoracionJuego entity.
 */
@SuppressWarnings("unused")
public interface ValoracionJuegoRepository extends JpaRepository<ValoracionJuego,Long> {

    @Query("select valoracionJuego from ValoracionJuego valoracionJuego where valoracionJuego.user.login = ?#{principal.username}")
    List<ValoracionJuego> findByUserIsCurrentUser();


    List<ValoracionJuego> findByJuego(Juego juego);

}
