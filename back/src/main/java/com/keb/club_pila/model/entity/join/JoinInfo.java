package com.keb.club_pila.model.entity.join;

import com.keb.club_pila.model.entity.BaseTimeEntity;
import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.entity.user.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Member member;

}
