package com.keb.club_pila.repository;

import com.keb.club_pila.model.entity.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findUserByUsername(String username);
}
