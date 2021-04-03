package com.keb.club_pila.model.entity.join;

import com.keb.club_pila.model.entity.BaseTimeEntity;
import com.keb.club_pila.model.entity.course.Course;
import com.keb.club_pila.model.entity.user.User;
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
    private Course course;

    @ManyToOne
    private User user;
    @Builder
    public JoinInfo(Course course, User member){
        this.course = course;
        this.user = user;
    }
}
