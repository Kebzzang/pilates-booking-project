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

    @Column(length = 10, nullable = false)
    private String name;

    @OneToMany(mappedBy = "teachers",cascade=CascadeType.ALL)
    @JsonIgnoreProperties({"teachers"})
    private Set<Course> courses = new HashSet<>();

    @ColumnDefault("1")
    private boolean working;

    public void update(String name, boolean working){
        this.name=name;
        this.working=working;
    }

}
