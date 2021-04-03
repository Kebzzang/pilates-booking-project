package com.keb.club_pila.model.entity.course;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keb.club_pila.model.entity.BaseTimeEntity;
import com.keb.club_pila.model.entity.join.JoinInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    @ManyToOne
    private Teacher teachers;

    @OneToMany(mappedBy="course")
    @JsonIgnoreProperties({"course"})
    private Set<JoinInfo> joins=new HashSet<>();

    private LocalDateTime courseDateTime;


    @Builder
    public Course(String title, String content, Teacher teacher) {
        this.title = title;
        this.content = content;
        this.teachers = teacher;
    }

    public void settingTeacher(Teacher teacher) {
        this.teachers = teacher;
    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
