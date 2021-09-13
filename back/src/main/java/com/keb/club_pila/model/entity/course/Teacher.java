package com.keb.club_pila.model.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keb.club_pila.model.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
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

    @Column(length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "teachers",cascade=CascadeType.ALL)
    @JsonIgnoreProperties({"teachers"})
    private Set<Course> courses = new HashSet<>();

    @ColumnDefault("1")
    private boolean working;

    private String email;
    private String userProfileImageLink; //for s3

    @Column(columnDefinition = "TEXT")
    private String about;

    public void updateTeacherProfileImageLink(String userProfileImageLink){
        this.userProfileImageLink=userProfileImageLink;
    }
    public void update(String name, boolean working, String email, String about){
        this.name=name;
        this.email=email;
        this.about=about;
        this.working=working;
    }

}
