package com.keb.club_pila.repository;

import com.keb.club_pila.model.entity.join.JoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinInfoRepository extends JpaRepository<JoinInfo, Long> {
    Long countAllByCourse_id(Long course_id);
}
