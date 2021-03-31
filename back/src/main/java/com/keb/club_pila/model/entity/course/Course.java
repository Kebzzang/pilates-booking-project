package com.keb.club_pila.model.entity.course;


import com.keb.club_pila.model.entity.BaseTimeEntity;
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
public class Course extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=500, nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    private Teacher teachers;
     Teacher getTeachers(){
        return teachers;
    }
    private String teacher;
    @Builder
    public Course(String title, String content, String teacher){
        this.title=title;
        this.content=content;
        this.teacher=teacher;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }

  /*  @Override
    public String toString() {
        System.out.println(getTeachers().getName()+":::"+getTeachers().getName());
        return null;
    }*/
}
