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
    @JoinColumn(name="teacher_id")
    private Teacher teachers;

    @OneToMany(mappedBy="course", cascade=CascadeType.ALL)
    @JsonIgnoreProperties({"course"})
    private Set<JoinInfo> joins=new HashSet<>();

    private LocalDateTime courseDateTime;


    @Builder
    public Course(EquipmentType equipmentType, String title, String content, Teacher teachers, LocalDateTime courseDateTime) {
        this.title = title;
        this.content = content;
        this.teachers = teachers;
        this.equipmentType=equipmentType;
    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
