package cc.doctor.stars.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RsCollectRequest {
    @NotNull(message = "资源ID不能为空")
    private Integer resId;
    @NotNull(message = "收藏状态不能为空")
    private Integer collect;
}
