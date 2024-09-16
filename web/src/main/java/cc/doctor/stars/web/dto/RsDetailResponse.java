package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Resources;
import cc.doctor.stars.biz.model.RsAweme;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RsDetailResponse extends RsResponse {
    private Integer collectStatus;
    private String subtitle;
    private Integer followStatus;
    private AwemeDetail awemeDetail;

    public RsDetailResponse(Resources resources, RsAweme aweme) {
        super(resources);
        if (aweme != null) {
            this.awemeDetail = new AwemeDetail();
            this.awemeDetail.setAwemeId(aweme.getAwemeId());
            this.awemeDetail.setWidth(aweme.getAwVWidth());
            this.awemeDetail.setHeight(aweme.getAwVHeight());
            this.awemeDetail.setDuration(aweme.getAwVDuration());
            this.awemeDetail.setPlayUrl(aweme.getAwVPlayUrl());
            this.awemeDetail.setAuthorId(aweme.getAuthorId());
            this.awemeDetail.setAwTitle(aweme.getAwTitle());
            this.awemeDetail.setAwCreateTime(LocalDateTime.ofEpochSecond(aweme.getAwCreateTime(), 0, ZoneOffset.ofHours(8)));
            this.awemeDetail.setAwCoverUrl(aweme.getAwCoverUrl());
        }

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class AwemeDetail extends Aweme {
        private String awemeId;
        private Integer width;
        private Integer height;
        private Integer duration;
        private String playUrl;
    }
}
