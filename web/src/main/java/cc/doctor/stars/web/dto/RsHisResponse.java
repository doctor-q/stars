package cc.doctor.stars.web.dto;

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

    public RsHisResponse(RsHistory rsHistory, RsResponse rsResponse) {
        super(rsResponse);
        this.viewTime = rsHistory.getCreateTime();
    }
}
