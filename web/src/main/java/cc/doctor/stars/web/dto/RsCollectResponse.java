package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Resources;
import cc.doctor.stars.biz.model.RsAweme;
import cc.doctor.stars.biz.model.RsCollect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RsCollectResponse extends RsResponse {
    private LocalDateTime collectTime;

    public RsCollectResponse(RsCollect rsCollect, RsResponse rsResponse) {
        super(rsResponse);
        this.collectTime = rsCollect.getCollectTime();
    }
}
