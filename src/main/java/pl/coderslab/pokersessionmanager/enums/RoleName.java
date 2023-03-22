package pl.coderslab.pokersessionmanager.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleName {
    ROLE_ADMIN,
    ROLE_ANONYMOUS,
    ROLE_MODERATOR,

    ROLE_USER,
}
