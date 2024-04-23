/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sawdev.cnss.plainte.repository;

import com.sawdev.cnss.plainte.entity.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface UserDao extends JpaRepository<User, Long> {

    String USERS_BY_USERNAME_CACHE = "usersByUsername";

    List<User> findAllByEnabledIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByUsernameOrEmail(String username, String email);
}
