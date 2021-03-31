package com.keb.club_pila.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=10, nullable=false)
    private String name;

    @OneToMany(mappedBy="teachers")
 //   @JsonIgnoreProperties({"teachers"})
    private Set<Course> courses=new HashSet<>();

}
