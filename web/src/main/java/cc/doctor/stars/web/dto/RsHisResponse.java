package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Resources;
import cc.doctor.stars.biz.model.RsAweme;
import cc.doctor.stars.biz.model.RsHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RsHisResponse extends RsResponse {
    private LocalDateTime viewTime;

    public RsHisResponse(RsHistory rsHistory, Resources resources, RsAweme rsAweme) {
        super(resources, rsAweme);
        this.viewTime = rsHistory.getCreateTime();
    }
}
