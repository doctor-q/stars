package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.model.RsCollect;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class RsCollectRequest {
    @NotNull(message = "资源ID不能为空")
    private Integer resId;
    @NotNull(message = "收藏状态不能为空")
    private Integer collect;

    public RsCollect update(Integer id) {
        RsCollect rsCollect = new RsCollect();
        rsCollect.setId(id);
        if (collect == YesOrNoEnum.YES.getValue()) {
            rsCollect.setCollectStatus(YesOrNoEnum.YES.getValue());
            rsCollect.setCollectTime(LocalDateTime.now());
        } else {
            rsCollect.setCollectStatus(YesOrNoEnum.NO.getValue());
        }
        return rsCollect;
    }

    public RsCollect insert(Integer userId) {
        RsCollect rsCollect = new RsCollect();
        rsCollect.setRsId(resId);
        rsCollect.setUserId(userId);
        rsCollect.setCollectStatus(YesOrNoEnum.YES.getValue());
        rsCollect.setCollectTime(LocalDateTime.now());
        return rsCollect;
    }
}
