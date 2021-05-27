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

    @Column( nullable = false)
    private Long maxStudent; //학생 수 제한
    @Column(columnDefinition = "BIGINT default 0")
    private Long nowStudent;
    @Column(nullable=false)
    private boolean isLocked;
    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teachers;

    @OneToMany(mappedBy="course", cascade=CascadeType.ALL)
    @JsonIgnoreProperties({"course"})
    private Set<JoinInfo> joins=new HashSet<>();

    private LocalDateTime courseDateTime;




    public void isLocked(){
        this.isLocked=true;
    }
    public void courseJoin(){
        this.nowStudent++;
    }
    public void courseCancel(){
        this.nowStudent--;
    }
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
