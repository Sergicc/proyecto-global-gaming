package com.globalgaming.repository;

import com.globalgaming.domain.UserExt;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserExt entity.
 */
@SuppressWarnings("unused")
public interface UserExtRepository extends JpaRepository<UserExt,Long> {

}
