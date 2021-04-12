package com.keb.club_pila.dto.user;


import com.keb.club_pila.model.entity.user.RoleType;
import com.keb.club_pila.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Getter
    @NoArgsConstructor
    public static class UserResponseDto {
        private Long id;
        private String username;
        private RoleType role;



        public UserResponseDto(User entity) {
            this.id=entity.getId();
            this.username=entity.getUsername();
            this.role=entity.getRole();
        }

        public UserResponseDto(String username) {
            this.username=username;

        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSaveRequestDto {
        private String username;
//        private RoleType role;
        private String password;

        public User toEntity(String encodedPassword) {
            return User.builder()
                    .password(encodedPassword)
                    .username(username)
                    .role(RoleType.ROLE_USER)
                    .build();

        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserUpdateDto {
        private RoleType role;
    }
}
