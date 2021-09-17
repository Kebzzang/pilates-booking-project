package com.keb.club_pila.model.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.keb.club_pila.model.entity.BaseTimeEntity;
import com.keb.club_pila.model.entity.join.JoinInfo;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username; //일반 유저네임

//    @Column(nullable = false, length=11)
//    private String phoneNumber;
//    private String oauth; //카카오, 구글, 일반 로그인인지 구분함
//    @Enumerated(EnumType.STRING) //DB에는 RoleType이라는 게 없어서 RoleType이 STRING이라고 명시함함
//    private RoleType role; //사실 enum을 쓰는 게 좋음. 회원이 회원가입->기본적으로 어드민(관리자), 유저, 매니저 등등 권한을 줄 수 있음

    @Column(nullable=false, length=100)
    private String certified;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role; //두 가지 롤 : USER, ADMIN

    @Column(nullable = false, length = 100)
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"member"})
    private Set<JoinInfo> joinedCourses = new HashSet<>();

    private String providerId;
    private String provider;

    public void updatePassword(String password) {
        this.password = password;
    }

    public Long updateCertified(String y){
        this.certified=y;
        System.out.println("certified"+certified+":::"+username);
        return 1L;
    }
    public void updateRole(String role){
        this.role=RoleType.valueOf(role.toUpperCase());
    }


}
