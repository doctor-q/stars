package cc.doctor.stars.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RsTypeEnum {
    DOUYIN(1), KUAISHOU(2);

    private final int type;
}
