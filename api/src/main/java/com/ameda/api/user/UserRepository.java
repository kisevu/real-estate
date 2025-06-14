package com.ameda.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: kev.Ameda
 */
@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
