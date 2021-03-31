package com.keb.club_pila.entity.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30, unique = true)
    private String username; //id에 해당
    @Column(nullable = false, length = 100) //password는 나중에 해쉬함수로 암호화할 예정
    private String password;
    @Column(nullable = false, length = 50)
    private String email;
    /* @ColumnDefault("'user'") //문자임을 알리기 위해 "''"    */

    @Column(nullable = false, length=11)
    private String phoneNumber;
    private String oauth; //카카오, 구글, 일반 로그인인지 구분함
//    @Enumerated(EnumType.STRING) //DB에는 RoleType이라는 게 없어서 RoleType이 STRING이라고 명시함함
//    private RoleType role; //사실 enum을 쓰는 게 좋음. 회원이 회원가입->기본적으로 어드민(관리자), 유저, 매니저 등등 권한을 줄 수 있음

    @Enumerated(EnumType.STRING)
    private RoleType role; //세가지 롤: 유저, 어드민, 선생님(teacher)



    @CreationTimestamp ///시간이 자동으로 입력됨 테이블에 인서트될 때 id와 같이 비워놔도 자동으로 채워짐
    private Timestamp createDate;


}
