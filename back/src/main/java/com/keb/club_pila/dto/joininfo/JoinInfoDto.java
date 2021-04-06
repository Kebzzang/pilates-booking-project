package com.keb.club_pila.dto.joininfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class JoinInfoDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinInfoSaveRequestDto{
        private Long course_id;
        private Long user_id;

    }

}
