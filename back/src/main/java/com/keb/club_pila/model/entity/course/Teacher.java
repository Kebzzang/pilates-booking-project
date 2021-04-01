package com.keb.club_pila.model.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keb.club_pila.model.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=10, nullable=false)
    private String name;

    @OneToMany(mappedBy="teachers")
    @JsonIgnoreProperties({"teachers"})
    private Set<Course> courses=new HashSet<>();

    private boolean isWorking;
    public Teacher(Long id){
        this.id=id;
    }
    public void adding(Course course){
        course.settingTeacher(this);
        this.courses.add(course);
    }
}
