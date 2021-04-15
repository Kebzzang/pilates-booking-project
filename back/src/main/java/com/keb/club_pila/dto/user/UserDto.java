package com.keb.club_pila.dto.user;


import com.keb.club_pila.model.entity.join.JoinInfo;
import com.keb.club_pila.model.entity.user.Member;
import com.keb.club_pila.model.entity.user.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

public class UserDto {
    @Getter
    @NoArgsConstructor
    public static class UserResponseSimpleDto {
        private String username;
        public UserResponseSimpleDto(Member member) {
            this.username=member.getUsername();
        }

    }
    @Getter
    @NoArgsConstructor
    public static class UserEmailDto{
        private String username;
        private String certified;

        public UserEmailDto(String username, String certified) {
            this.username = username;
            this.certified = certified;
        }
    }
    @Getter
    @NoArgsConstructor
    public static class UserResponseDto {
        private Long id;
        private String username;
        private RoleType role;



        public UserResponseDto(Member entity) {
            this.id=entity.getId();
            this.username=entity.getUsername();
            this.role=entity.getRole();
        }


    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSaveRequestDto {
        private String username;
//        private RoleType role;
        private String password;


        public Member toEntity(String encodedPassword) {
            return Member.builder()
                    .certified(certified_key())
                    .password(encodedPassword)
                    .username(username)
                    .role(RoleType.ROLE_USER)
                    .build();

        }
        private String certified_key() {
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            int num = 0;

            do {
                num = random.nextInt(75) + 48;
                if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                    sb.append((char) num);
                } else {
                    continue;
                }

            } while (sb.length() < 10);
            return sb.toString();
        }

    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserUpdateDto {
        private String password;
    }
}
