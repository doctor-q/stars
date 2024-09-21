package cc.doctor.stars.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RsFileTypeEnum {
    VIDEO(1, "视频");

    private final int type;
    private final String name;
}
