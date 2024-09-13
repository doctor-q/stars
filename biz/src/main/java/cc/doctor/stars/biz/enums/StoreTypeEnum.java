package cc.doctor.stars.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreTypeEnum {
    FILE(0, "本地"),
    OSS(1, "OSS");

    private final int type;
    private final String name;

    public static StoreTypeEnum getByType(Integer type) {
        if (type == null) {
            return null;
        }
        for (StoreTypeEnum value : values()) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }
}
