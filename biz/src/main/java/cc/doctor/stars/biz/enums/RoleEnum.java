package cc.doctor.stars.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    FATHER(0, "父亲"),
    MOTHER(1, "母亲"),
    GRANDPA(2, "爷爷"),
    GRANDMA(3, "奶奶"),
    GRANDFATHER(4, "外公"),
    GRANDMOTHER(5, "外婆"),
    ;

    private final int role;
    private final String name;

    public static String getName(Integer role) {
        if (role == null) {
            return null;
        }
        for (RoleEnum value : values()) {
            if (value.role == role) {
                return value.name;
            }
        }
        return null;
    }

}
