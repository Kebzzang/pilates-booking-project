package com.keb.club_pila.model.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ROLE_USER("ROLE_USER", "회원"), ROLE_ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
