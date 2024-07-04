package cc.doctor.stars.biz.enums;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * 是否状态，1-是，0-否
 * @author zhangyouxun
 * @since 2020-03-03
 */
@Getter
public enum YesOrNoEnum {

    YES(1, "是"),
    NO(0, "否");

    private final int value;
    private final String name;

    YesOrNoEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameByValue(Integer value) {
        if (value == null) {
            return "";
        }
        for (YesOrNoEnum e : values()) {
            if (e.getValue() == value) {
                return e.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (YesOrNoEnum e : values()) {
            if (e.getName().equals(name)) {
                return e.getValue();
            }
        }
        return null;
    }

}