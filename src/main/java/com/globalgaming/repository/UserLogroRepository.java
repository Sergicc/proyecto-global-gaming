package com.globalgaming.repository;

import com.globalgaming.domain.UserLogro;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserLogro entity.
 */
@SuppressWarnings("unused")
public interface UserLogroRepository extends JpaRepository<UserLogro,Long> {

    @Query("select userLogro from UserLogro userLogro where userLogro.user.login = ?#{principal.username}")
    List<UserLogro> findByUserIsCurrentUser();

}
